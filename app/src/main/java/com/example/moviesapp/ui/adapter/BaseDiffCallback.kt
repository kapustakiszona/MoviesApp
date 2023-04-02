package com.example.moviesapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback(
    private val oldItems: List<BaseListItem>,
    private val newItems: List<BaseListItem>
) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return oldItems[p0].getViewId() == newItems[p1].getViewId()
    }

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        return oldItems[p0].isItemEquals(newItems[p1])
    }

}