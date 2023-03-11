package com.omkarcodes.hackathonstarter.ui.usp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkarcodes.hackathonstarter.databinding.ItemChipBinding

class ChipsAdapter(
    val list: List<String>,
    val listener: OnClickListener? = null
) : RecyclerView.Adapter<ChipsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: ItemChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                chip.text = list[position]
                root.setOnClickListener { listener?.onClick(position) }
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}