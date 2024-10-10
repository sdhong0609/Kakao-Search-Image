package com.hongstudio.ui.base

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<ITEM : BaseViewHolderItem> : DiffUtil.ItemCallback<ITEM>() {
    override fun areItemsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
        return oldItem == newItem
    }
}
