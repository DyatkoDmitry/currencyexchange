package com.example.currency_exchange.modules

import android.content.Context
import com.example.currency_exchange.model.ItemStateService
import dagger.Module
import dagger.Provides

@Module(includes = [ModuleContext::class])
class ModuleStates {

    @Provides
    fun getItemStateService(context: Context): ItemStateService {
        return ItemStateService(context)
    }

}