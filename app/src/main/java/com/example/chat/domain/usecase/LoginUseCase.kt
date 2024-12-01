package com.example.chat.domain.usecase

import com.example.chat.common.ResponseResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.chat.data.remote.dto.login.request.LoginRequestDto
import com.example.chat.domain.mapper.toLoginResponse
import com.example.chat.domain.model.login.LoginResponse
import com.example.chat.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(requestDto: LoginRequestDto): Flow<ResponseResource<LoginResponse>> =
        flow {
            repository.login(requestDto).collect {
                when (it) {
                    is ResponseResource.Error -> emit(ResponseResource.error(it.errorMessage.toLoginResponse()))
                    is ResponseResource.Success -> emit(ResponseResource.success(it.data.toLoginResponse()))
                }
            }
        }
}