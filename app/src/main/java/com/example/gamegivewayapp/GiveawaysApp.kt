package com.example.gamegivewayapp

import android.app.Application
import com.example.gamegivewayapp.di.applicationModule
import com.example.gamegivewayapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiveawaysApp : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@GiveawaysApp)
            modules(listOf(networkModule, applicationModule))
        }
    }
}