package com.hongstudio.image_detail

import androidx.lifecycle.SavedStateHandle
import com.hongstudio.common.DefaultJson
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.toDomain
import com.hongstudio.core.domain.usecase.DeleteDocumentUseCase
import com.hongstudio.core.domain.usecase.GetSavedDocumentsUseCase
import com.hongstudio.core.domain.usecase.InsertDocumentUseCase
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
    getSavedDocumentsUseCase: GetSavedDocumentsUseCase,
    private val insertDocumentUseCase: InsertDocumentUseCase,
    private val deleteDocumentUseCase: DeleteDocumentUseCase
) : BaseViewModel() {

    private val item: DocumentModel? = (savedStateHandle["item"] ?: "").let {
        if (it.isBlank()) {
            null
        } else {
            DefaultJson.decodeFromString<DocumentModel>(it)
        }
    }

    private val detailItemStream: StateFlow<DocumentModel?> = getSavedDocumentsUseCase().map { documents ->
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
                deleteDocumentUseCase(data.toDomain())
            } else {
                insertDocumentUseCase(data.copy(isFavorite = true).toDomain())
            }
        }
    }
}
