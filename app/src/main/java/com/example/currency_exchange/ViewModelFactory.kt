package com.example.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.LocalStateService
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(val localStateService: LocalStateService, val apiService: APIService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return ViewModelMy(localStateService, apiService) as T
    }
}
