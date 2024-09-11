package com.hongstudio.kakaosearchimage.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.ui.base.BaseViewHolder

class ItemSearchedViewHolder(
    parent: ViewGroup,
    private val onClickFavorite: (item: DocumentDto) -> Unit,
    private val onClickItem: (item: DocumentDto) -> Unit
) : BaseViewHolder<ItemSearchedBinding, DocumentDto>(
    ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    override fun bind(item: DocumentDto) {
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
