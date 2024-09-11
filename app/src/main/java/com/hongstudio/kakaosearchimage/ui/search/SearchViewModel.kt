package com.hongstudio.kakaosearchimage.ui.search

import com.hongstudio.data.repository.DocumentRepository
import com.hongstudio.local.model.LocalDocument
import com.hongstudio.data.toLocal
import com.hongstudio.kakaosearchimage.BuildConfig
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
) : com.hongstudio.ui.base.BaseViewModel() {

    private val _searchedItems = MutableStateFlow(listOf<com.hongstudio.local.model.LocalDocument>())
    val searchedItems: StateFlow<List<com.hongstudio.local.model.LocalDocument>> = _searchedItems.asStateFlow()

    fun getSearchedItems(keyword: String) {
        if (keyword.isBlank()) return

        launch {
            val response = documentRepository.getSearchedImages(BuildConfig.REST_API_KEY, keyword)
            _searchedItems.update {
                response.documents.map { it.toLocal() }
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

    fun onClickFavorite(item: com.hongstudio.local.model.LocalDocument) {
        launch {
            if (item.isFavorite) {
                documentRepository.delete(item)
            } else {
                documentRepository.insert(item.copy(isFavorite = true))
            }
        }
    }
}
