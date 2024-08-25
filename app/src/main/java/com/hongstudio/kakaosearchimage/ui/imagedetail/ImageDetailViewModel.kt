package com.hongstudio.kakaosearchimage.ui.imagedetail

import com.hongstudio.kakaosearchimage.base.BaseViewModel
import com.hongstudio.kakaosearchimage.database.FavoriteDao
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ImageDetailViewModel(
    private val dao: FavoriteDao,
    private val detailItem: DocumentEntity?
) : BaseViewModel() {

    val detailItemStream: Flow<DocumentEntity?> = flowOf(detailItem)

    val isFavorite: StateFlow<Boolean> = combine(
        dao.getAll(),
        detailItemStream
    ) { favorites, detailItem ->
        favorites.any { it.thumbnailUrl == detailItem?.thumbnailUrl }
    }.stateIn(this, SharingStarted.WhileSubscribed(5000), false)

    fun onClickFavorite() {
        launch {
            val data = detailItem ?: return@launch
            if (isFavorite.value) {
                dao.delete(data)
            } else {
                dao.insert(data.copy(isFavorite = true))
            }
        }
    }
}
