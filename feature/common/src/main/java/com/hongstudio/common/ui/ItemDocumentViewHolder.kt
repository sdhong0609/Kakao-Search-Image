package com.hongstudio.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hongstudio.common.databinding.ItemDocumentBinding
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.ui.base.BaseViewHolder

class ItemDocumentViewHolder(
    parent: ViewGroup,
    private val onClickFavorite: (item: DocumentModel) -> Unit,
    private val onClickItem: (item: DocumentModel) -> Unit
) : BaseViewHolder<ItemDocumentBinding, DocumentModel>(
    ItemDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    override fun bind(item: DocumentModel) {
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
