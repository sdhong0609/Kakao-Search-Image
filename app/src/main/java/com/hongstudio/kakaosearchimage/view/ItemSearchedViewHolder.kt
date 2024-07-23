package com.hongstudio.kakaosearchimage.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

class ItemSearchedViewHolder(
    private val binding: ItemSearchedBinding,
    private val onClickFavorite: (data: DocumentEntity, position: Int) -> Unit,
    private val onClickItem: (data: DocumentEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: DocumentEntity) {
        Glide.with(binding.imageViewThumbnail)
            .load(data.thumbnailUrl)
            .into(binding.imageViewThumbnail)

        binding.textViewSiteName.text = data.displaySitename

        val starDrawable = if (data.isFavorite) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }
        Glide.with(binding.imageViewFavorite)
            .load(starDrawable)
            .into(binding.imageViewFavorite)

        binding.root.setOnClickListener {
            onClickItem(data)
        }
        binding.imageViewFavorite.setOnClickListener {
            onClickFavorite(data, adapterPosition)
        }
    }
}
