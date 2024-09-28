package com.hongstudio.search

import com.hongstudio.common.model.DocumentListItem

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data object Empty : SearchUiState
    data class Success(val items: List<DocumentListItem>) : SearchUiState
    data class Error(val error: Throwable?) : SearchUiState
}
