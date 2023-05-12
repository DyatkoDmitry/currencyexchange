package com.example.currency_exchange.modules

import android.content.Context
import com.example.currency_exchange.model.ResourceProvider
import dagger.Module
import dagger.Provides



@Module(includes = [ModuleContext::class])
class ModuleResources() {

    @Provides
    fun getResourceProvider(context: Context): ResourceProvider{
        return ResourceProvider(context)
    }


}