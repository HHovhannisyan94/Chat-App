package com.example.chat.data.remote.source

import com.example.chat.common.ResponseResource
import kotlinx.coroutines.flow.Flow
import com.example.chat.data.remote.dto.chatRoom.ChatRoomResponseDto
import com.example.chat.data.remote.dto.chatRoom.MessageResponseDto
import com.example.chat.data.remote.dto.friendList.response.FriendListResponseDto

interface ChatRemote {
    suspend fun getFriendList(token: String?): ResponseResource<FriendListResponseDto>
    suspend fun getRoomHistory(
        token: String?,
        receiver: String
    ): ResponseResource<ChatRoomResponseDto>

    suspend fun connectToSocket(
        sender: String,
        receiver: String,
        token: String
    ): ResponseResource<String>

    suspend fun sendMessage(message: String)
    fun receiveMessage(): Flow<MessageResponseDto>
    suspend fun disconnectSocket()
}