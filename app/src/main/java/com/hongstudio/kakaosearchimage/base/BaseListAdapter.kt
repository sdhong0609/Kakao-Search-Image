package com.hongstudio.kakaosearchimage.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<ITEM : Any>(
    diffCallback: DiffUtil.ItemCallback<ITEM>,
) : ListAdapter<ITEM, BaseViewHolder<ViewBinding, ITEM>>(diffCallback) {

    final override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, ITEM>, position: Int) {
        holder.bind(currentList[position])
    }

}
