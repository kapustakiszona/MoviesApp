package com.example.moviesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ChipsItemBinding

class ChipAdapter(
    var chipsList: List<Chip>,
    private val listener: AdapterListener
) :
    RecyclerView.Adapter<ChipAdapter.ChipViewHolder>() {


    class ChipViewHolder(private val binding: ChipsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myChip: Chip, listener: AdapterListener) = with(binding) {
            chip.text = myChip.name
            chip.isChecked = myChip.state
            chip.setOnClickListener {
                listener.onChipSelected(myChip)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChipsItemBinding.inflate(inflater, parent, false)

        return ChipViewHolder(binding)
    }

    override fun getItemCount(): Int = chipsList.size

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.bind(chipsList[position], listener)
    }
}