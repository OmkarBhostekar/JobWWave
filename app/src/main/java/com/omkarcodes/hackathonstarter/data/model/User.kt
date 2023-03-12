package com.omkarcodes.hackathonstarter.data.model

data class User(
    val __v: Int,
    val _id: String,
    val admin: Boolean,
    val email: String,
    val experience: Int,
    val firstname: String,
    val jobTitle: List<String>,
    val lastname: String,
    val layoff: Boolean,
    val location: String,
    val mobilenum: String,
    val password: String,
    val previousJobDesc: String,
    val pushToken: String,
    val skills: List<String>
)