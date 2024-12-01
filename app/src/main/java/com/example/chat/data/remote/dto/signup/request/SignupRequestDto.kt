package com.example.chat.data.remote.dto.signup.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupRequestDto(
    @SerialName("password")
    val password: String?,
    @SerialName("username")
    val username: String?
)