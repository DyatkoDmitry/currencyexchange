package com.example.currency_exchange.modules

import android.content.res.loader.ResourcesProvider
import com.example.currency_exchange.model.CurrencyAPIService
import com.example.currency_exchange.model.ResourceProvider
import com.example.currency_exchange.view.RecyclerAdapter
import dagger.Module
import dagger.Provides

@Module(includes = [ModuleResources::class, ModuleRetrofit::class])
class ModuleRecycler {

    @Provides
    fun getRecyclerAdapter(currencyAPIService: CurrencyAPIService, resourceProvider: ResourceProvider): RecyclerAdapter{
        return RecyclerAdapter(currencyAPIService, resourceProvider)
    }
}