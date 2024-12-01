package com.example.chat.domain.model.signup

data class SignupResponse(
    val token: String? = null,
    val username: String? = null,
    val errorMessage: String? = null
)
