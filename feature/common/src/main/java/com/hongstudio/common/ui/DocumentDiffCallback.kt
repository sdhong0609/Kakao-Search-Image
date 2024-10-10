package com.hongstudio.common.ui

import com.hongstudio.common.model.DocumentChangePayload
import com.hongstudio.common.model.DocumentListItem
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.ui.base.BaseDiffCallback

internal object DocumentDiffCallback : BaseDiffCallback<DocumentListItem>() {
    override fun areItemsTheSame(oldItem: DocumentListItem, newItem: DocumentListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DocumentListItem, newItem: DocumentListItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: DocumentListItem, newItem: DocumentListItem): Any? {
        return when {
            oldItem is DocumentModel && newItem is DocumentModel -> {
                if (oldItem.isFavorite != newItem.isFavorite) {
                    DocumentChangePayload.Favorite(newItem)
                } else {
                    super.getChangePayload(oldItem, newItem)
                }
            }

            else -> super.getChangePayload(oldItem, newItem)
        }
    }
}
