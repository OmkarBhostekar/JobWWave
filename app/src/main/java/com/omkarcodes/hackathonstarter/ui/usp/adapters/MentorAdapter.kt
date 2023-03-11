package com.omkarcodes.hackathonstarter.ui.usp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkarcodes.hackathonstarter.data.model.JobRef
import com.omkarcodes.hackathonstarter.data.model.Mentor
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.ItemMentorBinding
import com.omkarcodes.hackathonstarter.databinding.ItemRefBinding
import com.omkarcodes.hackathonstarter.databinding.ItemSmartSearchBinding

class MentorAdapter(
    val list: List<Mentor>,
    val listener: OnClickListener? = null
) : RecyclerView.Adapter<MentorAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMentorBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemMentorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            binding.apply {
                list[position].apply {
                    tvName.text = name
                    chipCompany.text = Company
                    chipExp.text = "${Exp.toInt()} Yrs"
                    ratingBar.setProgress((Rating*2).toInt(),true)
                    sessions.text = "${Sessions.toInt()} Sessions"
                    btnChat.setOnClickListener { listener?.onClick(this) }
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(result: Mentor)
    }
}