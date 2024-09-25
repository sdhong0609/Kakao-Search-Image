package com.hongstudio.common.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.common.model.DocumentModel
import com.hongstudio.ui.base.BaseListAdapter
import com.hongstudio.ui.base.BaseViewHolder

class DocumentListAdapter(
    private val onClickFavorite: (item: DocumentModel) -> Unit,
    private val onClickItem: (item: DocumentModel) -> Unit,
) : BaseListAdapter<DocumentModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, DocumentModel> {
        return ItemDocumentViewHolder(
            parent,
            onClickFavorite,
            onClickItem
        ) as BaseViewHolder<ViewBinding, DocumentModel>
    }
}
