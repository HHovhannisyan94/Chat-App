package com.example.chat.data.remote.source

import com.example.chat.common.ENDPOINT_CHAT_CONNECT
import com.example.chat.common.ENDPOINT_CHAT_HISTORY
import com.example.chat.common.ENDPOINT_FRIEND_LIST
import com.example.chat.common.ResponseResource
import com.example.chat.data.remote.dto.chatRoom.ChatRoomResponseDto
import com.example.chat.data.remote.dto.chatRoom.MessageResponseDto
import com.example.chat.data.remote.dto.friendList.response.FriendListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json

class ChatRemoteImpl(private val client: HttpClient) : ChatRemote {

    private var webSocket: WebSocketSession? = null

    override suspend fun getFriendList(token: String?): ResponseResource<FriendListResponseDto> =
        try {
            val response = client.get(ENDPOINT_FRIEND_LIST) {
                header("Authorization", "Bearer $token")
            }.body<FriendListResponseDto>()
            when (response.data) {
                null -> ResponseResource.error(response)
                else -> ResponseResource.success(response)
            }
        } catch (e: Exception) {
            ResponseResource.error(
                FriendListResponseDto
                    (error = FriendListResponseDto.Error("Error! Something bad happened"))
            )
        }

    override suspend fun getRoomHistory(
        token: String?,
        receiver: String
    ): ResponseResource<ChatRoomResponseDto> =
        try {
            val response = client.get(ENDPOINT_CHAT_HISTORY) {
                parameter("receiver", receiver)
                header(
                    "Authorization",
                    "Bearer $token"
                )
            }.body<ChatRoomResponseDto>()

            when (response.data) {
                null -> ResponseResource.error(response)
                else -> ResponseResource.success(response)
            }
        } catch (e: Exception) {
            ResponseResource.error(
                ChatRoomResponseDto
                    (error = ChatRoomResponseDto.Error("Error! Something bad happened"))
            )
        }

    override suspend fun connectToSocket(
        sender: String,
        receiver: String,
        token: String
    ): ResponseResource<String> = try {
        println("Websocket: CONNECTING")
        webSocket = client.webSocketSession {
            url(ENDPOINT_CHAT_CONNECT).apply {
                parameter("sender", sender)
                parameter("receiver", receiver)
                header(
                    "Authorization",
                    "Bearer $token"
                )
            }
        }
        if (webSocket?.isActive == true) {
            println("Websocket: CONNECTED")
            ResponseResource.success("Connected")
        } else {
            println("Websocket: CONNECTING FAILED")
            ResponseResource.error("Couldn't establish a connection.")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ResponseResource.error(e.localizedMessage ?: "Unknown error")
    }

    override suspend fun sendMessage(message: String) {
        try {
            webSocket?.send(Frame.Text(message))
            println("WebSocket: MessageSent -> $message")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun receiveMessage(): Flow<MessageResponseDto> = try {
        webSocket?.incoming
            ?.receiveAsFlow()
            ?.filter { it is Frame.Text }
            ?.map {
                val json = (it as? Frame.Text)?.readText().orEmpty()
                println("WebSocket: Message received -> $json")
                val messageResponseDto = Json.decodeFromString<MessageResponseDto>(json)
                messageResponseDto
            } ?: flow { }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyFlow()
    }

    override suspend fun disconnectSocket() {
        webSocket?.close()
        println("WebSocket: CLOSED")
    }
}