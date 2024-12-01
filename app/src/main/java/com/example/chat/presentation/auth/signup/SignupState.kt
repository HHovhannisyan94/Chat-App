package com.example.chat.presentation.auth.signup

import com.example.chat.domain.model.signup.SignupResponse

data class SignupState(
    val isLoading: Boolean = false,
    val data: SignupResponse? = null,
    val error: String = ""
)
