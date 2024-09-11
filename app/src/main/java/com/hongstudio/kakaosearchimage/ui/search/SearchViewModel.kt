package com.hongstudio.kakaosearchimage.ui.search

import com.hongstudio.data.DocumentRepository
import com.hongstudio.data.source.local.LocalDocument
import com.hongstudio.data.toLocal
import com.hongstudio.kakaosearchimage.BuildConfig
import com.hongstudio.kakaosearchimage.base.BaseViewModel
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

    private val _searchedItems = MutableStateFlow(listOf<LocalDocument>())
    val searchedItems: StateFlow<List<LocalDocument>> = _searchedItems.asStateFlow()

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

    fun onClickFavorite(item: LocalDocument) {
        launch {
            if (item.isFavorite) {
                documentRepository.delete(item)
            } else {
                documentRepository.insert(item.copy(isFavorite = true))
            }
        }
    }
}
