package com.example.currency_exchange.modules

import com.example.currency_exchange.model.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ModuleRetrofit {

    @Provides
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://us-central1-epam-laba-13-1527598553135.cloudfunctions.net/myWebsiteBackend/api/currency/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getAPIService(retrofit: Retrofit):APIService{
        return retrofit.create(APIService::class.java)
    }
}