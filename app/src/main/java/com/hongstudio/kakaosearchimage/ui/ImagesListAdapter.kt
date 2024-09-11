package com.hongstudio.kakaosearchimage.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.ui.base.BaseListAdapter
import com.hongstudio.ui.base.BaseViewHolder

class ImagesListAdapter(
    private val onClickFavorite: (item: DocumentDto) -> Unit,
    private val onClickItem: (item: DocumentDto) -> Unit,
) : BaseListAdapter<DocumentDto>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, DocumentDto> {
        return ItemSearchedViewHolder(
            parent,
            onClickFavorite,
            onClickItem
        ) as BaseViewHolder<ViewBinding, DocumentDto>
    }
}
