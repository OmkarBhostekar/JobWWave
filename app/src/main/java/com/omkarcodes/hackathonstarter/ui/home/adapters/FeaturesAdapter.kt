package com.omkarcodes.hackathonstarter.ui.home.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkarcodes.hackathonstarter.databinding.ItemFeatureBinding
import com.omkarcodes.hackathonstarter.ui.home.Feature

class FeaturesAdapter(
    val list: List<Feature>,
    val listener: OnClickListener
) : RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFeatureBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemFeatureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                card.setCardBackgroundColor(Color.parseColor(list[position].bg))
                imageView2.setImageResource(list[position].icon)
                textview.text = list[position].text
                root.setOnClickListener { listener.onClick(position) }
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}