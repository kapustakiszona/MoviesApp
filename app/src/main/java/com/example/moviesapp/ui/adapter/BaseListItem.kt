package com.example.moviesapp.ui.adapter

import android.view.View
import androidx.annotation.LayoutRes

interface BaseListItem {

    /**
     * Get this item layout resource id.
     *
     * @return id
     */
    @LayoutRes
    fun getViewId(): Int

    fun renderView(view: View, positionInAdapter: Int = 0)

    fun isItemEquals(another: BaseListItem): Boolean {
        return false
    }

}