package com.hongstudio.kakaosearchimage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding

class ImagesAdapter(
    private val dataSet: List<Document>,
    private val onClickFavorite: (data: Document, position: Int) -> Unit
) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSearchedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Document) {
            Glide.with(binding.imageViewThumbnail.context).load(data.thumbnailUrl)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}
