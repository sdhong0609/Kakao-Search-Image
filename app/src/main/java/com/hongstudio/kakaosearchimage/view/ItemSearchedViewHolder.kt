package com.hongstudio.kakaosearchimage.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.kakaosearchimage.model.Document

class ItemSearchedViewHolder(
    private val binding: ItemSearchedBinding,
    private val onClickFavorite: (data: Document, position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Document) {
        Glide.with(binding.imageViewThumbnail.context)
            .load(data.thumbnailUrl)
            .into(binding.imageViewThumbnail)
        binding.textViewSiteName.text = data.displaySitename
        if (data.isFavorite) {
            Glide.with(binding.imageViewFavorite.context)
                .load(android.R.drawable.btn_star_big_on)
                .into(binding.imageViewFavorite)
        }

        binding.imageViewFavorite.setOnClickListener {
            onClickFavorite(data, adapterPosition)
        }
    }
}
