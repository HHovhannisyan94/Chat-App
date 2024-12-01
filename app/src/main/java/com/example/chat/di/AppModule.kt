package com.example.chat.di

import com.example.chat.common.ktorHttpClient
import org.koin.dsl.module


val appModule = module {
    single { ktorHttpClient }
}