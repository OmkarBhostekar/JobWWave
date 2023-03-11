package com.omkarcodes.hackathonstarter.ui.chat

import com.omkarcodes.hackathonstarter.ui.chat.model.ChatData

class ChatListClickListener(val clickListener: (ChatData) -> Unit) {
    fun onClick(chatData: ChatData) = clickListener(chatData)
}