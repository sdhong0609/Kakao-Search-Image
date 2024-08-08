package com.hongstudio.kakaosearchimage.ui.imagedetail

import com.hongstudio.kakaosearchimage.base.BaseViewModel
import com.hongstudio.kakaosearchimage.database.FavoriteDao
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageDetailViewModel(
    private val dao: FavoriteDao,
    detailItem: DocumentEntity?
) : BaseViewModel() {

    private val _detailItem = MutableStateFlow(detailItem)
    val detailItem: StateFlow<DocumentEntity?> = _detailItem.asStateFlow()

    init {
        launch {
            dao.getAll().collectLatest { favorites ->
                withContext(Dispatchers.Default) {
                    if (favorites.any { it.thumbnailUrl == _detailItem.value?.thumbnailUrl }) {
                        _detailItem.update { it?.copy(isFavorite = true) }
                    } else {
                        _detailItem.update { it?.copy(isFavorite = false) }
                    }
                }
            }
        }
    }

    fun onClickFavorite() {
        launch {
            val data = _detailItem.value ?: return@launch
            if (data.isFavorite) {
                dao.delete(data)
            } else {
                dao.insert(data.copy(isFavorite = true))
            }
        }
    }
}
