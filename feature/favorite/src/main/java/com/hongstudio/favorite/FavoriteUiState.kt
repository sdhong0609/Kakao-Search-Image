package com.hongstudio.favorite

import com.hongstudio.common.model.DocumentModel

sealed interface FavoriteUiState {
    data object Loading : FavoriteUiState
    data object Empty : FavoriteUiState
    data class Success(val items: List<DocumentModel>) : FavoriteUiState
    data class Error(val error: Throwable?) : FavoriteUiState
}

