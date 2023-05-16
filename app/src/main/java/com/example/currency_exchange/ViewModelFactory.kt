package com.example.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.ItemStateService
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(val itemStateService: ItemStateService,val apiService: APIService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return ViewModelMy(itemStateService, apiService) as T
    }
}
