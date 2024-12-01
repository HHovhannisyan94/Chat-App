package com.example.chat.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String?,
    val token: String?,
    val isLoggedIn: Boolean? = false
)