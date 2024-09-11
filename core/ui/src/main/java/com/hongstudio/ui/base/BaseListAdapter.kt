package com.hongstudio.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.hongstudio.data.common.BaseViewHolderItem

abstract class BaseListAdapter<ITEM : BaseViewHolderItem> : ListAdapter<ITEM, BaseViewHolder<ViewBinding, ITEM>>(
    object : DiffUtil.ItemCallback<ITEM>() {
        override fun areItemsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
            return oldItem == newItem
        }
    }
) {

    final override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, ITEM>, position: Int) {
        holder.bind(currentList[position])
    }

}
