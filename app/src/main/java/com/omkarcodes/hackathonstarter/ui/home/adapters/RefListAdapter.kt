package com.omkarcodes.hackathonstarter.ui.home.adapters

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omkarcodes.hackathonstarter.data.model.JobRef
import com.omkarcodes.hackathonstarter.data.model.ReferralModel
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.ItemRefBinding
import com.omkarcodes.hackathonstarter.databinding.ItemRefListBinding
import com.omkarcodes.hackathonstarter.databinding.ItemSmartSearchBinding

class RefListAdapter(
    val list: List<ReferralModel>,
    val listener: OnClickListener? = null
) : RecyclerView.Adapter<RefListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRefListBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemRefListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                list[position].apply {
                    tvTitle.text = name
                    chip.text = company
                    tvMessage.text = if(referral.isNotEmpty()) referral[0].message else ""
                    tvMessage.movementMethod = ScrollingMovementMethod.getInstance()
                    btnCopy.setOnClickListener { listener?.onCopy(if(referral.isNotEmpty()) referral[0].message else "") }
                }
            }
        }
    }

    interface OnClickListener {
        fun onCopy(msg: String)
    }
}