package com.hongstudio.kakaosearchimage.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.kakaosearchimage.model.Document

class ImagesAdapter(
    private val onClickFavorite: (data: Document, position: Int) -> Unit
) : RecyclerView.Adapter<ItemSearchedViewHolder>() {

    private var dataSet: List<Document> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchedViewHolder {
        val binding =
            ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSearchedViewHolder(binding, onClickFavorite)
    }

    override fun onBindViewHolder(holder: ItemSearchedViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun setData(dataSet: List<Document>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun setUpdatedDocument(document: Document, position: Int) {
        dataSet = dataSet.toMutableList().apply {
            set(position, document)
        }.toList()
        notifyItemChanged(position)
    }
}
