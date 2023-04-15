package com.example.moviesapp.ui.films.ui

import android.view.View
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ChipItemBinding
import com.example.moviesapp.models.Chip
import com.example.moviesapp.ui.adapter.BaseListItem

class ChipItem(val chipItem: Chip): BaseListItem {
    override fun getViewId(): Int = R.layout.chip_item
    override fun isItemEquals(another: BaseListItem): Boolean {
        return another is ChipItem && another.chipItem == chipItem
    }

    override fun renderView(view: View, positionInAdapter: Int) {
        with(ChipItemBinding.bind(view)){
            chip.text = chipItem.name
            chip.isChecked = chipItem.state
        }
    }
}
