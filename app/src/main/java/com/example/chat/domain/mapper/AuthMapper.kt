package com.example.chat.domain.mapper


import com.example.chat.data.remote.dto.login.response.LoginResponseDto
import com.example.chat.data.remote.dto.signup.response.SignupResponseDto
import com.example.chat.domain.model.login.LoginResponse
import com.example.chat.domain.model.signup.SignupResponse
import com.example.chat.domain.model.user.User

fun LoginResponseDto.toLoginResponse() = LoginResponse(
    token = data?.token,
    username = data?.user?.username,

    errorMessage = error?.message
)

fun SignupResponseDto.toSignupResponse() = SignupResponse(
    token = data?.token,
    username = data?.user?.username,
    errorMessage = error?.message
)

fun LoginResponse.toUser() = User(
    username = username,
    token = token
)

fun SignupResponse.toUser() = User(
    username = username,
    token = token
)