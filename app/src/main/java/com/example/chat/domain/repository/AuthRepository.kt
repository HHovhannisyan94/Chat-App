package com.example.chat.domain.repository

import com.example.chat.common.ResponseResource
import kotlinx.coroutines.flow.Flow
import com.example.chat.data.remote.dto.login.request.LoginRequestDto
import com.example.chat.data.remote.dto.login.response.LoginResponseDto
import com.example.chat.data.remote.dto.signup.request.SignupRequestDto
import com.example.chat.data.remote.dto.signup.response.SignupResponseDto

interface AuthRepository {
    suspend fun signup(requestDto: SignupRequestDto): Flow<ResponseResource<SignupResponseDto>>
    suspend fun login(request: LoginRequestDto): Flow<ResponseResource<LoginResponseDto>>
}