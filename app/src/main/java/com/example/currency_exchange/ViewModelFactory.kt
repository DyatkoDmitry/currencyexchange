package com.example.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currency_exchange.model.ItemService
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val itemService: ItemService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return ViewModelMy(itemService) as T
    }
}
