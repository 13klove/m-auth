package com.m.one.message.token

data class RevokeRequest(
    val token: String,
    val userId: Long
)
