package com.example.chat.data.remote.dto.signup.response


import kotlinx.serialization.Serializable

@Serializable
data class SignupResponseDto(
    val data: Data? = null,
    val error: Error? = null
) {
    @Serializable
    data class Data(
        val token: String? = null,
        val user: User? = null
    ) {
        @Serializable
        data class User(
            val username: String? = null,
        )
    }

    @Serializable
    data class Error(
        val message: String? = null
    )
}