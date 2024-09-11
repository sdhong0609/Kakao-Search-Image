package com.hongstudio.kakaosearchimage.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.base.BaseListAdapter
import com.hongstudio.kakaosearchimage.base.BaseViewHolder

class ImagesListAdapter(
    private val onClickFavorite: (item: LocalDocument) -> Unit,
    private val onClickItem: (item: LocalDocument) -> Unit,
) : BaseListAdapter<LocalDocument>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, LocalDocument> {
        return ItemSearchedViewHolder(
            parent,
            onClickFavorite,
            onClickItem
        ) as BaseViewHolder<ViewBinding, LocalDocument>
    }
}
