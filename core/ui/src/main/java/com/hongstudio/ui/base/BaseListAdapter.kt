package com.hongstudio.ui.base

import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<ITEM : BaseViewHolderItem>(
    diffCallback: BaseDiffCallback<ITEM>
) : ListAdapter<ITEM, BaseViewHolder<ViewBinding, ITEM>>(diffCallback) {

    final override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, ITEM>, position: Int) {
        holder.bind(currentList[position])
    }
}
