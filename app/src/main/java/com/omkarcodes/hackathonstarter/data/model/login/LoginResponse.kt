package com.omkarcodes.hackathonstarter.data.model.login

data class LoginResponse(
    val name: String,
    val token: String,
    val userId: String
)