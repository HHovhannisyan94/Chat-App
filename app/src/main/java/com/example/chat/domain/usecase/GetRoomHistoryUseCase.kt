package com.example.chat.domain.usecase

import com.example.chat.common.ResponseResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.chat.domain.mapper.toRoomHistoryList
import com.example.chat.domain.model.chatRoom.RoomHistoryList
import com.example.chat.domain.repository.ChatRepository

class GetRoomHistoryUseCase(private val repository: ChatRepository) {

    suspend operator fun invoke(receiver: String):
            Flow<ResponseResource<RoomHistoryList>> = flow {
        repository.getRoomHistory(receiver).collect {
            val responseResource = when (it) {
                is ResponseResource.Error -> ResponseResource.error(it.errorMessage.toRoomHistoryList())
                is ResponseResource.Success -> ResponseResource.success(it.data.toRoomHistoryList())

            }
            emit(responseResource)
        }
    }
}