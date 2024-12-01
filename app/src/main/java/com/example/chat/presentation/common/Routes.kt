package com.example.chat.presentation.common

sealed class Routes(val route: String) {
    data object Splash : Routes("Splash")
    data object Login : Routes("Login")
    data object SignUp : Routes("SignUp")
    data object FriendsList : Routes("FriendsList")
    data object ChatRoom : Routes("ChatRoom") {
        const val ARG_FRIEND_NAME = "friendName"
        const val ARGS = "/{$ARG_FRIEND_NAME}"
    }
}