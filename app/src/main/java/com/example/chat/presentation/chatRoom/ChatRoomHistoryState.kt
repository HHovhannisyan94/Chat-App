package com.example.chat.presentation.chatRoom

import com.example.chat.domain.model.chatRoom.RoomHistoryList

data class ChatRoomHistoryState(
    val loading: Boolean = false,
    val data: List<RoomHistoryList.Message> = emptyList(),
    val error: String = ""
)
