package com.omkarcodes.hackathonstarter.data.model

data class RecResult(
    val _id: String,
    val benefits: List<String>,
    val company: String,
    val contactEmail: String,
    val creator: String,
    val datePosted: String,
    val description: String,
    val location: String,
    val minexp: Int,
    val qualifications: List<String>,
    val responsibilities: List<String>,
    val salary: Int,
    val skills: List<String>,
    val title: String
)