package com.hongstudio.favorite

import com.hongstudio.data.model.DocumentDto
import com.hongstudio.data.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : com.hongstudio.ui.base.BaseViewModel() {

    private val _favoriteItems = MutableStateFlow(listOf<DocumentDto>())
    val favoriteItems: StateFlow<List<DocumentDto>> = _favoriteItems.asStateFlow()

    init {
        launch {
            documentRepository.getAll().collectLatest { favorites ->
                _favoriteItems.update { favorites }
            }
        }
    }

    fun deleteFavorite(item: DocumentDto) {
        launch {
            documentRepository.delete(item)
        }
    }
}
