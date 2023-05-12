package com.example.currency_exchange.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ModuleContext(private val context: Context) {

    @Provides
    fun getApplicationContext(): Context{
        return context
    }
}