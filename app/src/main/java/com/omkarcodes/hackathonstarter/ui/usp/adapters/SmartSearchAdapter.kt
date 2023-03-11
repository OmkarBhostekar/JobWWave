package com.omkarcodes.hackathonstarter.ui.usp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.ItemSmartSearchBinding

class SmartSearchAdapter(
    val list: List<SearchResult>,
    val listener: OnClickListener? = null
) : RecyclerView.Adapter<SmartSearchAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSmartSearchBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemSmartSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                list[position].apply {
                    tvTitle.text = title
                    tvDesc.text = snippet
                    val temp = mutableListOf<String>()
                    rich_snippet?.top?.extensions?.forEach { temp.add(it) }
                    snippet_highlighted_words?.forEach { temp.add(it) }
                    rvChips.adapter = ChipsAdapter(temp)
                    root.setOnClickListener { listener?.onClick(this) }
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(result: SearchResult)
    }
}