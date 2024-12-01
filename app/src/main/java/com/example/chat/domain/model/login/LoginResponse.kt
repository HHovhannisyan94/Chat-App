package com.example.chat.domain.model.login

data class LoginResponse(
    val token: String? = null,
    val username: String? = null,
    val errorMessage: String? = null
)
