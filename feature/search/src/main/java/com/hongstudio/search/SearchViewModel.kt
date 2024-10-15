package com.hongstudio.search

import com.hongstudio.common.model.DocumentListItem
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.DocumentProgressbar
import com.hongstudio.common.model.toDomain
import com.hongstudio.common.model.toUiModel
import com.hongstudio.core.domain.usecase.DeleteDocumentUseCase
import com.hongstudio.core.domain.usecase.GetSavedDocumentsUseCase
import com.hongstudio.core.domain.usecase.GetSearchedImagesUseCase
import com.hongstudio.core.domain.usecase.InsertDocumentUseCase
import com.hongstudio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    getSavedDocumentsUseCase: GetSavedDocumentsUseCase,
    private val insertDocumentUseCase: InsertDocumentUseCase,
    private val deleteDocumentUseCase: DeleteDocumentUseCase,
    private val getSearchedImagesUseCase: GetSearchedImagesUseCase
) : BaseViewModel() {

    private val _searchedItems = MutableStateFlow<List<DocumentListItem>?>(null)

    private var searchedKeyword = ""

    val uiState: StateFlow<SearchUiState> = combine(
        getSavedDocumentsUseCase(),
        _searchedItems,
        isLoading
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

        launchWithLoading {
            _searchedItems.value = getSearchedImagesUseCase(
                keyword = searchedKeyword,
                page = INITIAL_PAGE
            ).map { it.toUiModel() }
        }
    }

    fun onClickFavorite(item: DocumentModel) {
        launch {
            if (item.isFavorite) {
                deleteDocumentUseCase(item.toDomain())
            } else {
                insertDocumentUseCase(item.copy(isFavorite = true).toDomain())
            }
        }
    }

    fun loadNextData(page: Int) {
        launch {
            if (page > PAGE_MAX) return@launch
            if (_searchedItems.value?.let { it.size < 10 } == true) return@launch
            if (_searchedItems.value?.lastOrNull() is DocumentProgressbar) return@launch

            _searchedItems.value = _searchedItems.value?.plus(DocumentProgressbar)
            val newItems = getSearchedImagesUseCase(
                keyword = searchedKeyword,
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
