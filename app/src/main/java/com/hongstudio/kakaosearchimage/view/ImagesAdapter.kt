package com.hongstudio.kakaosearchimage.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

class ImagesAdapter(
    private val onClickFavorite: (data: DocumentEntity, position: Int) -> Unit,
    private val onClickItem: (data: DocumentEntity) -> Unit,
) : ListAdapter<DocumentEntity, ItemSearchedViewHolder>(diffCallback) {

    private var dataSet: List<DocumentEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchedViewHolder {
        val binding =
            ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSearchedViewHolder(binding, onClickFavorite, onClickItem)
    }

    override fun onBindViewHolder(holder: ItemSearchedViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<DocumentEntity> =
            object : DiffUtil.ItemCallback<DocumentEntity>() {
                override fun areItemsTheSame(oldDocument: DocumentEntity, newDocument: DocumentEntity): Boolean {
                    return oldDocument.thumbnailUrl == newDocument.thumbnailUrl
                }

                override fun areContentsTheSame(oldDocument: DocumentEntity, newDocument: DocumentEntity): Boolean {
                    return oldDocument == newDocument
                }

            }
    }

    fun setData(dataSet: List<DocumentEntity>) {
        this.dataSet = dataSet
        submitList(dataSet)
    }

    fun setUpdatedDocument(documentEntity: DocumentEntity, position: Int) {
        dataSet = dataSet.toMutableList().apply {
            set(position, documentEntity)
        }
        submitList(dataSet)
    }
}
