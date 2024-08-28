package com.hongstudio.kakaosearchimage.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, ITEM : Any>(
    protected val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: ITEM)
}
