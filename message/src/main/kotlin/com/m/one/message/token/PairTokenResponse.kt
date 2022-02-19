package com.m.one.message.token

data class PairTokenResponse(
    val baseToken: TokenResponse,
    val refreshToken: TokenResponse
)
