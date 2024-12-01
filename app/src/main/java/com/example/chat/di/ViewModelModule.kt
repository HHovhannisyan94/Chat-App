package com.example.chat.di

import com.example.chat.presentation.auth.login.LoginViewModel
import com.example.chat.presentation.auth.signup.SignupViewModel
import com.example.chat.presentation.chatRoom.ChatRoomViewModel
import com.example.chat.presentation.friendsList.FriendListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignupViewModel(get(), get()) }
    viewModel { FriendListViewModel(get(), get()) }
    viewModel { ChatRoomViewModel(get(), get(), get()) }
}