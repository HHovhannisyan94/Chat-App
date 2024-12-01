package com.example.chat.di

import com.example.chat.data.repository.AuthRepositoryImpl
import com.example.chat.data.repository.ChatRepositoryImpl
import com.example.chat.domain.repository.AuthRepository
import com.example.chat.domain.repository.ChatRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ChatRepository> { ChatRepositoryImpl(get(), get()) }
}