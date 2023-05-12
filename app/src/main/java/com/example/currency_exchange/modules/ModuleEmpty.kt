package com.example.currency_exchange.modules

import com.example.currency_exchange.model.Cell
import dagger.Module
import dagger.Provides

@Module
class ModuleEmpty {

    @Provides
    fun getInt():Int{
        return 7
    }


}