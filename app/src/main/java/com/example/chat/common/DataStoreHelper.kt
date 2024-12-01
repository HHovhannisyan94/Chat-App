package com.example.chat.common

import com.example.chat.domain.model.user.User

interface DataStoreHelper {
   suspend fun saveUser(user: User)
   suspend fun getUser(): User?
   suspend fun clearSession()
}