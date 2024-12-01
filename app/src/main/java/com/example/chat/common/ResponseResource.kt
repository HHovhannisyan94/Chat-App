package com.example.chat.common


sealed class ResponseResource<T> {
    data class Success<T>(val data: T) : ResponseResource<T>()
    data class Error<T>(val errorMessage: T) : ResponseResource<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(errorMessage: T) = Error(errorMessage)
    }
}
