package com.example.currency_exchange.model

import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET(" ")
    suspend fun getAllRates():List<Rate>

    @GET("{base}")
    suspend fun getCertainRate(@Path("base") base:String): Rate
}