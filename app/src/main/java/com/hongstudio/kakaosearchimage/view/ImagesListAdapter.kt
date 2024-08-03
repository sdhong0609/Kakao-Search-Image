package com.hongstudio.kakaosearchimage.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hongstudio.kakaosearchimage.base.BaseListAdapter
import com.hongstudio.kakaosearchimage.databinding.ItemSearchedBinding
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

class ImagesListAdapter(
    private val onClickFavorite: (item: DocumentEntity) -> Unit,
    private val onClickItem: (item: DocumentEntity) -> Unit,
) : BaseListAdapter<DocumentEntity>(
    diffCallback = object : DiffUtil.ItemCallback<DocumentEntity>() {
        override fun areItemsTheSame(oldDocument: DocumentEntity, newDocument: DocumentEntity): Boolean {
            return oldDocument.thumbnailUrl == newDocument.thumbnailUrl
        }

        override fun areContentsTheSame(oldDocument: DocumentEntity, newDocument: DocumentEntity): Boolean {
            return oldDocument == newDocument
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchedViewHolder {
        val binding =
            ItemSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSearchedViewHolder(binding, onClickFavorite, onClickItem)
    }
}
