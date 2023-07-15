package com.example.currency_exchange.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ModuleContext {

    @Provides
    fun getApplicationContext(app: Application): Context{
        return app.applicationContext
    }
}
