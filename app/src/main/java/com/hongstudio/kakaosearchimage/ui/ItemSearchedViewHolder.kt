package com.hongstudio.kakaosearchimage.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hongstudio.kakaosearchimage.base.BaseViewHolder
import com.hongstudio.kakaosearchimage.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding

class ItemSearchedViewHolder(
    parent: ViewGroup,
    private val onClickFavorite: (item: LocalDocument) -> Unit,
    private val onClickItem: (item: LocalDocument) -> Unit
) : BaseViewHolder<ItemSearchedBinding, LocalDocument>(
    ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    override fun bind(item: LocalDocument) {
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
