package com.example.chat.domain.usecase

import com.example.chat.common.ResponseResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.chat.domain.mapper.toFriendList
import com.example.chat.domain.model.friendList.FriendList
import com.example.chat.domain.repository.ChatRepository

class FriendListUseCase(private val repository: ChatRepository) {

    suspend operator fun invoke(): Flow<ResponseResource<FriendList>> = flow {
        repository.getFriendList().collect {
            val responseResource = when (it) {
                is ResponseResource.Error -> ResponseResource.error(it.errorMessage.toFriendList())
                is ResponseResource.Success -> ResponseResource.success(it.data.toFriendList())
            }
            emit(responseResource)
        }
    }
}