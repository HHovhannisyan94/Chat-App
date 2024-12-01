package com.example.chat.di


import com.example.chat.common.DataStoreHelper
import com.example.chat.common.DataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val cacheModule = module {
    single <DataStoreHelper>{ DataStoreHelperImpl(androidContext()) }
}