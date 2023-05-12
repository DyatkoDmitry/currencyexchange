package com.example.currency_exchange.modules

import com.example.currency_exchange.model.Cell
import com.example.currency_exchange.model.ResourceProvider
import javax.inject.Inject

class EmptyClass(val name: String){
    @Inject lateinit var resourceProvider:ResourceProvider

    fun getListCells(): List<Cell>{
        return resourceProvider.getListCells()
    }
}