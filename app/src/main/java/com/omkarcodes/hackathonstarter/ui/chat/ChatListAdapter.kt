package com.omkarcodes.hackathonstarter.ui.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omkarcodes.hackathonstarter.databinding.ChatListItemBinding
import com.omkarcodes.hackathonstarter.ui.chat.model.ChatData

class ChatListAdapter(private val clickListener: ChatListClickListener): ListAdapter<ChatData, ChatListAdapter.ChatListViewHolder>(
    ChatListDiffUtil()
) {
    class ChatListDiffUtil: DiffUtil.ItemCallback<ChatData>() {
        override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem.strangeeId == newItem.strangeeId
        }

        override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding(item, clickListener)
    }

    class ChatListViewHolder(val binding: ChatListItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun binding(item: ChatData, clickListener: ChatListClickListener) {
//            binding.chatData = item
            binding.apply {
                personName.text = item.firstName + " " + item.lastName
                message.text = item.message
                Glide.with(root.context)
                    .load(item.imageUrl)
                    .into(profilePic)
                root.setOnClickListener { clickListener.clickListener(item) }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ChatListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatListItemBinding.inflate(layoutInflater, parent, false)
                return ChatListViewHolder(binding)
            }
        }
    }
}
