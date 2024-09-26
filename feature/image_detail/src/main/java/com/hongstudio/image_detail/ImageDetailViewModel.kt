package com.hongstudio.image_detail

import androidx.lifecycle.SavedStateHandle
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.toDto
import com.hongstudio.data.repository.DocumentRepository
import com.hongstudio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private val item: StateFlow<String> = savedStateHandle.getStateFlow("item", "")

    val detailItemStream: StateFlow<DocumentModel?> = item.map {
        if (it.isBlank()) {
            null
        } else {
            Json.decodeFromString<DocumentModel>(it)
        }
    }.stateIn(this, SharingStarted.WhileSubscribed(), null)

    val isFavorite: StateFlow<Boolean> = combine(
        documentRepository.getAll(),
        detailItemStream
    ) { favorites, detailItem ->
        favorites.any { it.thumbnailUrl == detailItem?.thumbnailUrl }
    }.stateIn(this, SharingStarted.WhileSubscribed(), false)

    fun onClickFavorite() {
        launch {
            val data = detailItemStream.value ?: return@launch
            if (isFavorite.value) {
                documentRepository.delete(data.toDto())
            } else {
                documentRepository.insert(data.copy(isFavorite = true).toDto())
            }
        }
    }
}
