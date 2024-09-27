package com.hongstudio.favorite

import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.toDto
import com.hongstudio.common.model.toUiModel
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.data.repository.DocumentRepository
import com.hongstudio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private val savedDocuments: Flow<List<DocumentDto>> = documentRepository.getAll()

    val favoriteItems: StateFlow<List<DocumentModel>> = savedDocuments.map {
        it.map { dto -> dto.toUiModel() }
    }.stateIn(
        this,
        SharingStarted.WhileSubscribed(),
        listOf()
    )

    fun deleteFavorite(item: DocumentModel) {
        launch {
            documentRepository.delete(item.toDto())
        }
    }
}
