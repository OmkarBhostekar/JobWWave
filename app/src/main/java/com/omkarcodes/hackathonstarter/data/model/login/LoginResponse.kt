package com.omkarcodes.hackathonstarter.data.model.login

import com.omkarcodes.hackathonstarter.data.model.User


data class LoginResponse(
    val user: User,
    val token: String,
)