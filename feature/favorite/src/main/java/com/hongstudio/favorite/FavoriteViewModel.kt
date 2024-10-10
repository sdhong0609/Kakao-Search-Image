package com.hongstudio.favorite

import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.toDomain
import com.hongstudio.common.model.toUiModel
import com.hongstudio.core.domain.usecase.DeleteDocumentUseCase
import com.hongstudio.core.domain.usecase.GetSavedDocumentsUseCase
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
    getSavedDocumentsUseCase: GetSavedDocumentsUseCase,
    private val deleteDocumentUseCase: DeleteDocumentUseCase
) : BaseViewModel() {

    val uiState: StateFlow<FavoriteUiState> = getSavedDocumentsUseCase().map {
        if (it.isEmpty()) {
            FavoriteUiState.Empty
        } else {
            FavoriteUiState.Success(it.map { dto ->
                dto.toUiModel().copy(isFavorite = true)
            })
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
            deleteDocumentUseCase(item.toDomain())
        }
    }
}
