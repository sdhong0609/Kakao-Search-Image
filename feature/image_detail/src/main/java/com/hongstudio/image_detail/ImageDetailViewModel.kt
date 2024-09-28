package com.hongstudio.image_detail

import androidx.lifecycle.SavedStateHandle
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.toDto
import com.hongstudio.data.DefaultJson
import com.hongstudio.data.repository.DocumentRepository
import com.hongstudio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private val savedDocuments = documentRepository.getAll()

    private val item: DocumentModel? = (savedStateHandle["item"] ?: "").let {
        if (it.isBlank()) {
            null
        } else {
            DefaultJson.decodeFromString<DocumentModel>(it)
        }
    }

    private val detailItemStream: StateFlow<DocumentModel?> = savedDocuments.map { documents ->
        item?.copy(isFavorite = documents.any { it.thumbnailUrl == item.thumbnailUrl })
    }.stateIn(this, SharingStarted.WhileSubscribed(), item)

    val uiState: StateFlow<ImageDetailUiState> = detailItemStream.map {
        if (it == null) {
            ImageDetailUiState.NotFound
        } else {
            ImageDetailUiState.Found(it)
        }
    }.catch {
        emit(ImageDetailUiState.NotFound)
    }.stateIn(this, SharingStarted.WhileSubscribed(), ImageDetailUiState.Loading)

    fun onClickFavorite() {
        launch {
            val data = detailItemStream.value ?: return@launch
            if (data.isFavorite) {
                documentRepository.delete(data.toDto())
            } else {
                documentRepository.insert(data.copy(isFavorite = true).toDto())
            }
        }
    }
}
