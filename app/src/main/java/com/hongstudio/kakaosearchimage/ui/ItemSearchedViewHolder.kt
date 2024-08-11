package com.hongstudio.kakaosearchimage.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hongstudio.kakaosearchimage.base.BaseViewHolder
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

class ItemSearchedViewHolder(
    parent: ViewGroup,
    private val onClickFavorite: (item: DocumentEntity) -> Unit,
    private val onClickItem: (item: DocumentEntity) -> Unit
) : BaseViewHolder<ItemSearchedBinding, DocumentEntity>(
    ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    override fun bind(item: DocumentEntity) {
        binding.imageViewThumbnail.load(item.thumbnailUrl)
        binding.textViewSiteName.text = item.displaySitename

        val starDrawable = if (item.isFavorite) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }
        binding.imageViewFavorite.load(starDrawable)

        binding.root.setOnClickListener {
            onClickItem(item)
        }
        binding.imageViewFavorite.setOnClickListener {
            onClickFavorite(item)
        }
    }
}
