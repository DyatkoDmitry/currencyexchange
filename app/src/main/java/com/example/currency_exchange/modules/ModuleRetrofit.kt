package com.example.currency_exchange.modules

import com.example.currency_exchange.model.CurrencyAPIService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ModuleRetrofit {

    @Provides
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://us-central1-epam-laba-13-1527598553135.cloudfunctions.net/myWebsiteBackend/api/currency/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getAPIService(retrofit: Retrofit):CurrencyAPIService{
        return retrofit.create(CurrencyAPIService::class.java)
    }
}