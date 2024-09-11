package com.hongstudio.kakaosearchimage.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hongstudio.local.model.LocalDocument
import com.hongstudio.kakaosearchimage.base.BaseViewHolder
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding

class ItemSearchedViewHolder(
    parent: ViewGroup,
    private val onClickFavorite: (item: com.hongstudio.local.model.LocalDocument) -> Unit,
    private val onClickItem: (item: com.hongstudio.local.model.LocalDocument) -> Unit
) : BaseViewHolder<ItemSearchedBinding, com.hongstudio.local.model.LocalDocument>(
    ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    override fun bind(item: com.hongstudio.local.model.LocalDocument) {
        binding.imageViewThumbnail.load(item.thumbnailUrl)
        binding.textViewSiteName.text = item.displaySitename
        binding.imageViewFavorite.isSelected = item.isFavorite

        binding.root.setOnClickListener {
            onClickItem(item)
        }
        binding.imageViewFavorite.setOnClickListener {
            onClickFavorite(item)
        }
    }
}
