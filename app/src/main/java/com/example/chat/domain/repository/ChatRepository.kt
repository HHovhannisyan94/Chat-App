package com.example.chat.domain.repository

import com.example.chat.common.ResponseResource
import com.example.chat.data.remote.dto.chatRoom.ChatRoomResponseDto
import kotlinx.coroutines.flow.Flow

import com.example.chat.data.remote.dto.chatRoom.MessageResponseDto
import com.example.chat.data.remote.dto.friendList.response.FriendListResponseDto


interface ChatRepository {
    suspend fun getFriendList(): Flow<ResponseResource<FriendListResponseDto>>
    suspend fun getRoomHistory(receiver: String): Flow<ResponseResource<ChatRoomResponseDto>>

    suspend fun connectToSocket(sender: String, receiver: String): ResponseResource<String>
    suspend fun sendMessage(message: String)
    fun receiveMessage(): Flow<MessageResponseDto>
    suspend fun disconnectSocket()
}