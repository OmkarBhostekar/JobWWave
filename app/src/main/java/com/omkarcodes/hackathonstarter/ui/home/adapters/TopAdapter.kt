package com.omkarcodes.hackathonstarter.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omkarcodes.hackathonstarter.data.model.RecResult
import com.omkarcodes.hackathonstarter.databinding.ItemTopBinding
import com.omkarcodes.hackathonstarter.ui.usp.adapters.ChipsAdapter

class TopAdapter(
    val list: List<RecResult>,
    val listener: OnClickListener?
) : RecyclerView.Adapter<TopAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTopBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemTopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                list[position].apply {
                    Glide.with(root.context)
                        .load("https://api.dicebear.com/5.x/initials/png?seed=$company")
                        .into(ivImage)
                    textView4.text = "$company - $title"
                    tvDesc.text = description
                    val temp = mutableListOf<String>()
                    temp.add(salary.toString())
                    skills.forEach { temp.add(it) }
                    qualifications.forEach { temp.add(it) }
                    rvChips.adapter = ChipsAdapter(temp)
                    tvLocation.text = location
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(result: RecResult)
    }

}