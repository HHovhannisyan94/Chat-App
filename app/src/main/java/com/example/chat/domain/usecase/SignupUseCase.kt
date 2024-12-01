package com.example.chat.domain.usecase

import com.example.chat.common.ResponseResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.chat.data.remote.dto.signup.request.SignupRequestDto
import com.example.chat.domain.mapper.toSignupResponse
import com.example.chat.domain.model.signup.SignupResponse
import com.example.chat.domain.repository.AuthRepository

class SignupUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(request: SignupRequestDto): Flow<ResponseResource<SignupResponse>> =
        flow {
            repository.signup(request).collect {
                when (it) {
                    is ResponseResource.Error-> emit(ResponseResource.error(it.errorMessage.toSignupResponse()))
                    is ResponseResource.Success -> emit(ResponseResource.success(it.data.toSignupResponse()))
                }
            }
        }
}