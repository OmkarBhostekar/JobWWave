package com.omkarcodes.hackathonstarter.ui.usp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkarcodes.hackathonstarter.data.model.JobRef
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.ItemRefBinding
import com.omkarcodes.hackathonstarter.databinding.ItemSmartSearchBinding

class RefAdapter(
    val list: List<JobRef>,
    val listener: OnClickListener? = null
) : RecyclerView.Adapter<RefAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRefBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemRefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                list[position].apply {
                    tvTitle.text = name
                    if(position<3) chipFire.visibility = View.VISIBLE
                    else chipFire.visibility = View.GONE
                    chip.text = company
                    root.setOnClickListener { listener?.onClick(this) }
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(result: JobRef)
    }
}