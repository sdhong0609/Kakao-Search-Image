package com.hongstudio.kakaosearchimage.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.kakaosearchimage.model.Document

class ImagesAdapter(
    private val onClickFavorite: (data: Document, position: Int) -> Unit
) : ListAdapter<Document, ItemSearchedViewHolder>(diffCallback) {

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

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<Document> = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(oldDocument: Document, newDocument: Document): Boolean {
                return oldDocument.thumbnailUrl == newDocument.thumbnailUrl
            }

            override fun areContentsTheSame(oldDocument: Document, newDocument: Document): Boolean {
                return oldDocument == newDocument
            }

        }
    }

    fun setData(dataSet: List<Document>) {
        this.dataSet = dataSet
        submitList(dataSet)
    }

    fun setUpdatedDocument(document: Document, position: Int) {
        dataSet = dataSet.toMutableList().apply {
            set(position, document)
        }
        submitList(dataSet)
    }
}
