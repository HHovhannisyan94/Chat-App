package com.example.chat.domain.model.friendList

data class FriendList(
    val friendInfo: List<FriendInfo>? = null,
    val errorMessage: String? = null
) {
    data class FriendInfo(
        val token: String,
        val username: String,
        val avatar: String,
        val lastMessage: LastMessage? = null
    ) {
        data class LastMessage(
            val textMessage: String?,
            val timestamp: Long?
        )
    }
}
