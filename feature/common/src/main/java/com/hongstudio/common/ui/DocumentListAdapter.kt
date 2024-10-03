package com.hongstudio.common.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.common.model.DocumentListItem
import com.hongstudio.common.model.DocumentListItemType
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.common.model.DocumentProgressbar
import com.hongstudio.ui.base.BaseListAdapter
import com.hongstudio.ui.base.BaseViewHolder

class DocumentListAdapter(
    private val onClickFavorite: (item: DocumentModel) -> Unit,
    private val onClickItem: (item: DocumentModel) -> Unit,
) : BaseListAdapter<DocumentListItem>() {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            DocumentProgressbar -> DocumentListItemType.PROGRESSBAR.ordinal
            else -> DocumentListItemType.DOCUMENT.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, DocumentListItem> {
        return when (viewType) {
            DocumentListItemType.PROGRESSBAR.ordinal ->
                ItemProgressbarViewHolder(parent) as BaseViewHolder<ViewBinding, DocumentListItem>

            else -> ItemDocumentViewHolder(
                parent,
                onClickFavorite,
                onClickItem
            ) as BaseViewHolder<ViewBinding, DocumentListItem>
        }
    }
}
