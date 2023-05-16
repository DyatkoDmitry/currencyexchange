package com.example.currency_exchange.modules

import com.example.currency_exchange.Adapter
import com.example.currency_exchange.model.ItemState
import com.example.currency_exchange.model.ItemStateService
import dagger.Module
import dagger.Provides

@Module(includes = [ModuleStates::class, ModuleRetrofit::class])
class ModuleAdapter {

    @Provides
    fun getAdapter(itemStateService: ItemStateService): Adapter {
        return Adapter(itemStateService.getStates())
    }
}