package com.hongstudio.kakaosearchimage.ui.imagedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.hongstudio.kakaosearchimage.GlobalApplication
import com.hongstudio.kakaosearchimage.database.FavoriteDatabase
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

class ImageDetailViewModelFactory(private val detailItem: DocumentEntity?): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val application = checkNotNull(extras[APPLICATION_KEY]) as GlobalApplication
        val dao = FavoriteDatabase.getDatabase(application).documentDao()
        return ImageDetailViewModel(dao, detailItem) as T
    }
}
