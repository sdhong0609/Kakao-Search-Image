package com.hongstudio.kakaosearchimage.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.local.model.LocalDocument
import com.hongstudio.kakaosearchimage.base.BaseListAdapter
import com.hongstudio.kakaosearchimage.base.BaseViewHolder

class ImagesListAdapter(
    private val onClickFavorite: (item: com.hongstudio.local.model.LocalDocument) -> Unit,
    private val onClickItem: (item: com.hongstudio.local.model.LocalDocument) -> Unit,
) : BaseListAdapter<com.hongstudio.local.model.LocalDocument>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, com.hongstudio.local.model.LocalDocument> {
        return ItemSearchedViewHolder(
            parent,
            onClickFavorite,
            onClickItem
        ) as BaseViewHolder<ViewBinding, com.hongstudio.local.model.LocalDocument>
    }
}
