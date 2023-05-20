package com.example.currency_exchange.model

import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET(" ")
    suspend fun getAllRemoteStates():List<RemoteState>

    @GET("{base}")
    suspend fun getCertainRemoteState(@Path("base") base:String): RemoteState
}