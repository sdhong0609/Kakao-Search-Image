package com.hongstudio.kakaosearchimage.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.hongstudio.kakaosearchimage.BuildConfig
import com.hongstudio.kakaosearchimage.GlobalApplication
import com.hongstudio.kakaosearchimage.base.BaseViewModel
import com.hongstudio.kakaosearchimage.database.FavoriteDao
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import com.hongstudio.kakaosearchimage.service.RetrofitObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val dao: FavoriteDao
) : BaseViewModel() {

    private val _searchedItems = MutableStateFlow(listOf<DocumentEntity>())
    val searchedItems: StateFlow<List<DocumentEntity>> = _searchedItems.asStateFlow()

    fun getSearchedItems(keyword: String) {
        if (keyword.isBlank()) return

        launch {
            val response = RetrofitObject.searchImageService.getSearchedImages(BuildConfig.REST_API_KEY, keyword)
            _searchedItems.update {
                response.documents.map { it.toEntity() }
            }

            updateFavorites()
        }
    }

    private fun updateFavorites() {
        launch {
            dao.getAll().collectLatest { favorites ->
                val updatedItems = withContext(Dispatchers.Default) {
                    _searchedItems.value.map { documentEntity ->
                        if (favorites.any { it.thumbnailUrl == documentEntity.thumbnailUrl }) {
                            documentEntity.copy(isFavorite = true)
                        } else {
                            documentEntity.copy(isFavorite = false)
                        }
                    }
                }
                _searchedItems.update { updatedItems }
            }
        }
    }

    fun onClickFavorite(item: DocumentEntity) {
        launch {
            if (item.isFavorite) {
                dao.delete(item)
            } else {
                dao.insert(item.copy(isFavorite = true))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as GlobalApplication
                val dao = FavoriteDatabase.getDatabase(application).documentDao()
                return SearchViewModel(dao) as T
            }
        }
    }
}
