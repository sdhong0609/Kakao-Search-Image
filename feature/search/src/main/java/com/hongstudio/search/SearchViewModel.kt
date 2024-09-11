package com.hongstudio.search

import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.toDto
import com.hongstudio.common.model.toUiModel
import com.hongstudio.data.repository.DocumentRepository
import com.hongstudio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private val _searchedItems = MutableStateFlow(listOf<DocumentModel>())
    val searchedItems: StateFlow<List<DocumentModel>> = _searchedItems.asStateFlow()

    fun getSearchedItems(keyword: String) {
        if (keyword.isBlank()) return

        launch {
            val items = documentRepository.getSearchedImages(BuildConfig.REST_API_KEY, keyword)
            _searchedItems.update {
                items.map { it.toUiModel() }
            }

            updateFavorites()
        }
    }

    private fun updateFavorites() {
        launch {
            documentRepository.getAll().collectLatest { favorites ->
                val updatedItems = withContext(Dispatchers.Default) {
                    _searchedItems.value.map { localDocument ->
                        if (favorites.any { it.thumbnailUrl == localDocument.thumbnailUrl }) {
                            localDocument.copy(isFavorite = true)
                        } else {
                            localDocument.copy(isFavorite = false)
                        }
                    }
                }
                _searchedItems.update { updatedItems }
            }
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
