package com.example.chat.di

import com.example.chat.data.remote.source.AuthRemote
import com.example.chat.data.remote.source.AuthRemoteImpl
import com.example.chat.data.remote.source.ChatRemote
import com.example.chat.data.remote.source.ChatRemoteImpl
import org.koin.dsl.module


val networkModule = module {
    single<AuthRemote> { AuthRemoteImpl(get()) }
    single<ChatRemote> { ChatRemoteImpl(get()) }
}