package com.example.currency_exchange.modules

import com.example.currency_exchange.Adapter
import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.AdapterFactory
import com.example.currency_exchange.model.LocalState
import com.example.currency_exchange.model.LocalStateService
import dagger.Module
import dagger.Provides

@Module(includes = [ModuleStates::class, ModuleRetrofit::class])
class ModuleAdapter {

    @Provides
    fun getListItems(localStateService: LocalStateService): List<LocalState> {
        return localStateService.getLocalStates()
    }
}