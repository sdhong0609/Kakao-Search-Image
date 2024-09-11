package com.hongstudio.kakaosearchimage.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.local.model.LocalDocument
import com.hongstudio.ui.base.BaseListAdapter
import com.hongstudio.ui.base.BaseViewHolder

class ImagesListAdapter(
    private val onClickFavorite: (item: com.hongstudio.local.model.LocalDocument) -> Unit,
    private val onClickItem: (item: com.hongstudio.local.model.LocalDocument) -> Unit,
) : com.hongstudio.ui.base.BaseListAdapter<com.hongstudio.local.model.LocalDocument>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.hongstudio.ui.base.BaseViewHolder<ViewBinding, com.hongstudio.local.model.LocalDocument> {
        return ItemSearchedViewHolder(
            parent,
            onClickFavorite,
            onClickItem
        ) as com.hongstudio.ui.base.BaseViewHolder<ViewBinding, com.hongstudio.local.model.LocalDocument>
    }
}
