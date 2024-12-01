package com.example.chat.di

import com.example.chat.domain.usecase.FriendListUseCase
import com.example.chat.domain.usecase.GetRoomHistoryUseCase
import com.example.chat.domain.usecase.LoginUseCase
import com.example.chat.domain.usecase.SignupUseCase
import org.koin.dsl.module


val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { SignupUseCase(get()) }
    single { FriendListUseCase(get()) }
    single { GetRoomHistoryUseCase(get()) }
}