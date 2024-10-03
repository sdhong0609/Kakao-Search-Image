package com.hongstudio.image_detail

import com.hongstudio.common.model.DocumentModel

sealed interface ImageDetailUiState {
    data object Loading : ImageDetailUiState
    data class Found(val item: DocumentModel) : ImageDetailUiState
    data object NotFound : ImageDetailUiState
}
