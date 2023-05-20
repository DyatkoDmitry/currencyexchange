package com.example.currency_exchange.model

import com.example.currency_exchange.Adapter
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AdapterFactory {
    fun create(listRemoteStates: List<RemoteState>): Adapter
}