package com.omkarcodes.hackathonstarter.ui.chat.model
data class ChatData(
    val strangeeId: String,
    val firstName: String,
    val lastName: String,
    val imageUrl: String,
    val timestamp: Long,
    val isRead: Boolean,
    val message: String,
    val isOnline: Boolean,
    val country: String = "",
    val gender: String = "",
    val interestedIn: List<String> = emptyList(),
    val birthday: Long = 0,
    val aboutMe: String = "",
    var saved: Boolean = false,
)
