package com.example.chat.presentation.auth.login

import com.example.chat.domain.model.login.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val data: LoginResponse? = null,
    val error: String = ""
)
