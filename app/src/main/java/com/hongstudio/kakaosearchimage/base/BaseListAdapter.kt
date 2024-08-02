package com.hongstudio.kakaosearchimage.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<ITEM : Any>(
    diffCallback: DiffUtil.ItemCallback<ITEM>,
) : ListAdapter<ITEM, BaseViewHolder<ITEM>>(diffCallback) {

    private var items: List<ITEM> = listOf()

    final override fun onBindViewHolder(holder: BaseViewHolder<ITEM>, position: Int) {
        holder.bind(items[holder.adapterPosition])
    }

    final override fun getItemCount(): Int = items.size

    fun setData(items: List<ITEM>) {
        this.items = items
        submitList(items)
    }

    fun updateItem(item: ITEM, position: Int) {
        items = items.toMutableList().apply {
            set(position, item)
        }
        submitList(items)
    }
}
