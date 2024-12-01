package com.example.chat.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.chat.presentation.auth.login.LoginScreen
import com.example.chat.presentation.auth.signup.SignUpScreen
import com.example.chat.presentation.chatRoom.ChatRoomScreen
import com.example.chat.presentation.common.Routes
import com.example.chat.presentation.friendsList.FriendsListScreen
import com.example.chat.presentation.splash.MainScreen

@Composable
fun MainScreenContainer() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) {
            MainScreen(navController)
        }
        composable(Routes.Login.route) {
            LoginScreen(navController)
        }
        composable(Routes.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(Routes.FriendsList.route) {
            FriendsListScreen(navController)
        }
        composable(route = "${Routes.ChatRoom.route}${Routes.ChatRoom.ARGS}", arguments = listOf(
            navArgument(Routes.ChatRoom.ARG_FRIEND_NAME) { type = NavType.StringType }

        )) {
            ChatRoomScreen(
                navController,
                friendName = it.arguments?.getString(Routes.ChatRoom.ARG_FRIEND_NAME).orEmpty()

            )
        }
    }
}
