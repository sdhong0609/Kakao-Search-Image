package com.hongstudio.kakaosearchimage.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.hongstudio.kakaosearchimage.GlobalApplication
import com.hongstudio.kakaosearchimage.base.BaseViewModel
import com.hongstudio.kakaosearchimage.database.FavoriteDao
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val dao: FavoriteDao
) : BaseViewModel() {

    private val _favoriteItems = MutableStateFlow(listOf<DocumentEntity>())
    val favoriteItems: StateFlow<List<DocumentEntity>> = _favoriteItems.asStateFlow()

    init {
        launch {
            dao.getAll().collectLatest { favorites ->
                _favoriteItems.update { favorites }
            }
        }
    }

    fun deleteFavorite(item: DocumentEntity) {
        launch {
            dao.delete(item)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as GlobalApplication
                val dao = FavoriteDatabase.getDatabase(application).documentDao()
                return FavoriteViewModel(dao) as T
            }
        }
    }
}
