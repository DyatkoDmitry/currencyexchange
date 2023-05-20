package com.example.currency_exchange.modules

import android.content.Context
import com.example.currency_exchange.model.LocalStateService
import dagger.Module
import dagger.Provides

@Module(includes = [ModuleContext::class])
class ModuleStates {

    @Provides
    fun getLocalStateService(context: Context): LocalStateService {
        return LocalStateService(context)
    }
}