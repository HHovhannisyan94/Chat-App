package com.example.chat.data.repository

import com.example.chat.common.DataStoreHelper
import com.example.chat.common.ResponseResource
import com.example.chat.data.remote.dto.chatRoom.ChatRoomResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.chat.data.remote.dto.chatRoom.MessageResponseDto
import com.example.chat.data.remote.dto.friendList.response.FriendListResponseDto
import com.example.chat.data.remote.source.ChatRemote
import com.example.chat.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val remote: ChatRemote,
    private val dataStoreHelper: DataStoreHelper
) : ChatRepository {

    override suspend fun getFriendList(): Flow<ResponseResource<FriendListResponseDto>> =
        flow {
            val responseResource =
                when (val response = remote.getFriendList(dataStoreHelper.getUser()?.token)) {
                    is ResponseResource.Error -> ResponseResource.error(response.errorMessage)
                    is ResponseResource.Success -> ResponseResource.success(response.data)
                }
            emit(responseResource)
        }

    override suspend fun getRoomHistory(
        receiver: String
    ): Flow<ResponseResource<ChatRoomResponseDto>> = flow {
        val responseResource =
            when (val response = remote.getRoomHistory(dataStoreHelper.getUser()?.token, receiver)) {
                is ResponseResource.Error -> ResponseResource.error(response.errorMessage)
                is ResponseResource.Success -> ResponseResource.success(response.data)
            }

        emit(responseResource)
    }

    override suspend fun connectToSocket(
        sender: String,
        receiver: String
    ): ResponseResource<String> {
        return when (val response =
            remote.connectToSocket(sender, receiver, dataStoreHelper.getUser()?.token.orEmpty())) {
            is ResponseResource.Error -> ResponseResource.error(response.errorMessage)
            is ResponseResource.Success -> ResponseResource.success(response.data)

        }
    }

    override suspend fun sendMessage(message: String) {
        remote.sendMessage(message)
    }

    override fun receiveMessage(): Flow<MessageResponseDto> = remote.receiveMessage()

    override suspend fun disconnectSocket() {
        remote.disconnectSocket()
    }
}