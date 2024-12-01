package com.example.chat.data.repository

import com.example.chat.common.ResponseResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.chat.data.remote.dto.login.request.LoginRequestDto
import com.example.chat.data.remote.dto.login.response.LoginResponseDto
import com.example.chat.data.remote.dto.signup.request.SignupRequestDto
import com.example.chat.data.remote.dto.signup.response.SignupResponseDto
import com.example.chat.data.remote.source.AuthRemote
import com.example.chat.domain.repository.AuthRepository

class AuthRepositoryImpl(private val remote: AuthRemote) : AuthRepository {

    override suspend fun signup(requestDto: SignupRequestDto): Flow<ResponseResource<SignupResponseDto>> =
        flow {
            when (val response = remote.signup(requestDto)) {
                is ResponseResource.Error -> emit(ResponseResource.error(response.errorMessage))
                is ResponseResource.Success -> emit(ResponseResource.success(response.data))
            }
        }

    override suspend fun login(request: LoginRequestDto): Flow<ResponseResource<LoginResponseDto>> =
        flow {
            when (val response = remote.login(request)) {
                is ResponseResource.Error -> emit(ResponseResource.error(response.errorMessage))
                is ResponseResource.Success -> emit(ResponseResource.success(response.data))
            }
        }
}