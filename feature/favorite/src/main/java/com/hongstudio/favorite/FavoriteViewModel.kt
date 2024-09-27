package com.hongstudio.favorite

import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.toDto
import com.hongstudio.common.model.toUiModel
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
class FavoriteViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    val uiState: StateFlow<FavoriteUiState> = documentRepository.getAll().map {
        if (it.isEmpty()) {
            FavoriteUiState.Empty
        } else {
            FavoriteUiState.Success(it.map { dto -> dto.toUiModel() })
        }
    }.catch {
        emit(FavoriteUiState.Error(it))
    }.stateIn(
        this,
        SharingStarted.WhileSubscribed(),
        FavoriteUiState.Loading
    )

    fun deleteFavorite(item: DocumentModel) {
        launch {
            documentRepository.delete(item.toDto())
        }
    }
}
