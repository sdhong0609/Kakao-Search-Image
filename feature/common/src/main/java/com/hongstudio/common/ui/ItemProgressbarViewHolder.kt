package com.hongstudio.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hongstudio.common.databinding.ItemProgressbarBinding
import com.hongstudio.common.model.DocumentProgressbar
import com.hongstudio.ui.base.BaseViewHolder

class ItemProgressbarViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<ItemProgressbarBinding, DocumentProgressbar>(
    ItemProgressbarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    override fun bind(item: DocumentProgressbar) = Unit
}
