package com.hongstudio.kakaosearchimage.ui.favorite

import com.hongstudio.data.DocumentRepository
import com.hongstudio.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.base.BaseViewModel
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
) : BaseViewModel() {

    private val _favoriteItems = MutableStateFlow(listOf<LocalDocument>())
    val favoriteItems: StateFlow<List<LocalDocument>> = _favoriteItems.asStateFlow()

    init {
        launch {
            documentRepository.getAll().collectLatest { favorites ->
                _favoriteItems.update { favorites }
            }
        }
    }

    fun deleteFavorite(item: LocalDocument) {
        launch {
            documentRepository.delete(item)
        }
    }
}
