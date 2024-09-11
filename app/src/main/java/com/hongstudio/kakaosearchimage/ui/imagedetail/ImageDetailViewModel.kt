package com.hongstudio.kakaosearchimage.ui.imagedetail

import androidx.lifecycle.SavedStateHandle
import com.hongstudio.data.DocumentRepository
import com.hongstudio.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    val detailItemStream: StateFlow<LocalDocument?> = savedStateHandle.getStateFlow("ImageDetailExtra", null)

    val isFavorite: StateFlow<Boolean> = combine(
        documentRepository.getAll(),
        detailItemStream
    ) { favorites, detailItem ->
        favorites.any { it.thumbnailUrl == detailItem?.thumbnailUrl }
    }.stateIn(this, SharingStarted.WhileSubscribed(5000), false)

    fun onClickFavorite() {
        launch {
            val data = detailItemStream.value ?: return@launch
            if (isFavorite.value) {
                documentRepository.delete(data)
            } else {
                documentRepository.insert(data.copy(isFavorite = true))
            }
        }
    }
}
