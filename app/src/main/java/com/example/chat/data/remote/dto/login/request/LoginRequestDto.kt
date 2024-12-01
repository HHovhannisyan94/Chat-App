package com.example.chat.data.remote.dto.login.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val username: String? = null,
    val password: String? = null
)
