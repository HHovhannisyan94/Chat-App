package com.example.chat.data.remote.source

import com.example.chat.common.ENDPOINT_LOGIN
import com.example.chat.common.ENDPOINT_SIGNUP
import com.example.chat.common.ResponseResource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

import com.example.chat.data.remote.dto.login.request.LoginRequestDto
import com.example.chat.data.remote.dto.login.response.LoginResponseDto
import com.example.chat.data.remote.dto.signup.request.SignupRequestDto
import com.example.chat.data.remote.dto.signup.response.SignupResponseDto

class AuthRemoteImpl(private val client: HttpClient) : AuthRemote {

    override suspend fun signup(request: SignupRequestDto): ResponseResource<SignupResponseDto> =
        try {
            val response = client.post(ENDPOINT_SIGNUP) {
                setBody(request)
            }.body<SignupResponseDto>()
            when (response.data) {
                null -> ResponseResource.error(response)
                else -> ResponseResource.success(response)
            }
        } catch (e: Exception) {
            ResponseResource.error(
                SignupResponseDto
                    (error = SignupResponseDto.Error("Error! Something bad happened"))
            )
        }

    override suspend fun login(request: LoginRequestDto): ResponseResource<LoginResponseDto> = try {
        val response = client.post(ENDPOINT_LOGIN) { setBody(request) }.body<LoginResponseDto>()
        when (response.data) {
            null -> ResponseResource.error(response)
            else -> ResponseResource.success(response)
        }
    } catch (e: Exception) {
        ResponseResource.error(
            LoginResponseDto
                (error = LoginResponseDto.Error("Error! Something bad happened"))
        )
    }
}