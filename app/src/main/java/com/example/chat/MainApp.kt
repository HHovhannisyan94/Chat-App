package com.example.chat

import android.app.Application
import com.example.chat.di.appModule
import com.example.chat.di.cacheModule
import com.example.chat.di.networkModule
import com.example.chat.di.repositoryModule
import com.example.chat.di.useCaseModule
import com.example.chat.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(
                appModule,
                cacheModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}