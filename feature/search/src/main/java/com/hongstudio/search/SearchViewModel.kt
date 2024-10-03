package com.hongstudio.search

import com.hongstudio.common.model.DocumentListItem
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.DocumentProgressbar
import com.hongstudio.common.model.toDto
import com.hongstudio.common.model.toUiModel
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.data.repository.DocumentRepository
import com.hongstudio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private val savedDocuments: Flow<List<DocumentDto>> = documentRepository.getAll()

    private val _searchedItems = MutableStateFlow<List<DocumentListItem>?>(null)

    private val _isLoading = MutableStateFlow(false)

    private var searchedKeyword = ""

    val uiState: StateFlow<SearchUiState> = combine(
        savedDocuments,
        _searchedItems,
        _isLoading
    ) { savedDocuments, searchedItems, isLoading ->
        when {
            isLoading -> SearchUiState.Loading
            searchedItems == null -> SearchUiState.Idle
            searchedItems.isEmpty() -> SearchUiState.Empty
            else -> {
                val updatedItems: List<DocumentListItem> = searchedItems.map { searchedItem ->
                    if (searchedItem is DocumentModel) {
                        if (savedDocuments.any { it.thumbnailUrl == searchedItem.thumbnailUrl }) {
                            searchedItem.copy(isFavorite = true)
                        } else {
                            searchedItem.copy(isFavorite = false)
                        }
                    } else {
                        searchedItem
                    }
                }
                SearchUiState.Success(updatedItems)
            }
        }
    }.catch {
        emit(SearchUiState.Error(it))
    }.stateIn(
        scope = this,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchUiState.Idle
    )

    fun getSearchedItems(keyword: String) {
        if (keyword.isBlank() || searchedKeyword == keyword) return

        searchedKeyword = keyword

        launch {
            _isLoading.value = true
            _searchedItems.value = documentRepository.getSearchedImages(
                query = searchedKeyword,
                page = INITIAL_PAGE
            ).map { it.toUiModel() }
            _isLoading.value = false
        }
    }

    fun onClickFavorite(item: DocumentModel) {
        launch {
            if (item.isFavorite) {
                documentRepository.delete(item.toDto())
            } else {
                documentRepository.insert(item.copy(isFavorite = true).toDto())
            }
        }
    }

    fun loadNextData(page: Int) {
        launch {
            if (page > PAGE_MAX) return@launch
            if (_searchedItems.value?.contains(DocumentProgressbar) == true) return@launch

            _searchedItems.value = _searchedItems.value?.plus(DocumentProgressbar)
            val newItems = documentRepository.getSearchedImages(
                query = searchedKeyword,
                page = page
            ).map { it.toUiModel() }
            _searchedItems.value = _searchedItems.value?.minus(DocumentProgressbar)?.plus(newItems)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val PAGE_MAX = 50
    }
}
