package com.example.chat.presentation.friendsList

import com.example.chat.domain.model.friendList.FriendList

data class FriendListState(
    val isLoading: Boolean = false,
    val data: List<FriendList.FriendInfo> = emptyList(),
    val error: String = ""
)
