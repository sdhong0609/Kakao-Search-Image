package com.hongstudio.kakaosearchimage.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.kakaosearchimage.base.BaseListAdapter
import com.hongstudio.kakaosearchimage.base.BaseViewHolder
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

class ImagesListAdapter(
    private val onClickFavorite: (item: DocumentEntity) -> Unit,
    private val onClickItem: (item: DocumentEntity) -> Unit,
) : BaseListAdapter<DocumentEntity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, DocumentEntity> {
        return ItemSearchedViewHolder(
            parent,
            onClickFavorite,
            onClickItem
        ) as BaseViewHolder<ViewBinding, DocumentEntity>
    }
}
