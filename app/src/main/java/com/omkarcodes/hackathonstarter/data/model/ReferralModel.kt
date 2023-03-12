package com.omkarcodes.hackathonstarter.data.model

data class ReferralModel(
    val _id: String,
    val name: String,
    val count: Int,
    val company: String,
    val referral: List<Referral>,
)

data class Referral(val message: String,)
