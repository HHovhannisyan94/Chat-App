package com.example.chat.data.remote.source

import com.example.chat.common.ResponseResource
import com.example.chat.data.remote.dto.login.request.LoginRequestDto
import com.example.chat.data.remote.dto.login.response.LoginResponseDto
import com.example.chat.data.remote.dto.signup.request.SignupRequestDto
import com.example.chat.data.remote.dto.signup.response.SignupResponseDto

interface AuthRemote {
    suspend fun login(request: LoginRequestDto): ResponseResource<LoginResponseDto>
    suspend fun signup(request: SignupRequestDto): ResponseResource<SignupResponseDto>
}