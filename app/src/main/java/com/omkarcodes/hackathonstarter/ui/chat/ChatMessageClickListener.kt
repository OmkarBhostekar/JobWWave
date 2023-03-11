package com.omkarcodes.hackathonstarter.ui.chat

import com.omkarcodes.hackathonstarter.ui.chat.model.Message

class ChatMessageClickListener(val clickListener: (Message) -> Unit) {
    fun onClick(message: Message) = clickListener(message)
}