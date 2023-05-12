package com.example.currency_exchange.model

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyAPIService {

    @GET(" ")
    suspend fun getAllCurrency():List<DataCurrency>

    @GET("{base}")
    suspend fun getCertainCurrency(@Path("base") base:String): DataCurrency
}