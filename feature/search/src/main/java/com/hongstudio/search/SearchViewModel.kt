package com.hongstudio.search

import com.hongstudio.common.model.DocumentModel
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private val savedDocuments: Flow<List<DocumentDto>> = documentRepository.getAll()

    private val _searchedItems = MutableStateFlow(listOf<DocumentModel>())
    val searchedItems: StateFlow<List<DocumentModel>> = combine(
        savedDocuments,
        _searchedItems
    ) { savedDocuments, searchedItems ->
        searchedItems.map { searchedItem ->
            if (savedDocuments.any { it.thumbnailUrl == searchedItem.thumbnailUrl }) {
                searchedItem.copy(isFavorite = true)
            } else {
                searchedItem.copy(isFavorite = false)
            }
        }
    }.stateIn(
        scope = this,
        started = SharingStarted.WhileSubscribed(),
        initialValue = listOf()
    )

    fun getSearchedItems(keyword: String) {
        if (keyword.isBlank()) return

        launch {
            _searchedItems.value = documentRepository.getSearchedImages(BuildConfig.REST_API_KEY, keyword)
                .map { it.toUiModel() }
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
}
