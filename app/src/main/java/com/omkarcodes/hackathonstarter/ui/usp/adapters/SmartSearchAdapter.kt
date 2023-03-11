package com.omkarcodes.hackathonstarter.ui.usp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.ItemSmartSearchBinding

class SmartSearchAdapter(
    val list: List<SearchResult>,
    val listener: OnClickListener? = null,
    val isFreelancing: Boolean = false
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
                    if(isFreelancing) imageView3.setImageResource(R.drawable.fiverr_1)
                    else imageView3.setImageResource(R.drawable.linkedin)
                    val temp = mutableListOf<String>()
                    rich_snippet?.top?.extensions?.forEach { temp.add(it) }
                    snippet_highlighted_words?.forEach { temp.add(it) }
                    tvDesc.isVisible = snippet.toString().isNotEmpty()
                    if(temp.size>0){
                        rvChips.visibility = View.VISIBLE
                        rvChips.adapter = ChipsAdapter(temp)
                    }else rvChips.visibility = View.GONE
                    root.setOnClickListener { listener?.onClick(this) }
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(result: SearchResult)
    }
}