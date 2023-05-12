package com.example.currency_exchange

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_exchange.model.Cell
import com.example.currency_exchange.model.CurrencyAPIService
import com.example.currency_exchange.model.DataCurrency
import com.example.currency_exchange.model.ResourceProvider
import com.example.currency_exchange.modules.EmptyClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import javax.inject.Inject

class ViewModelMy: ViewModel() {

    @Inject lateinit var resourceProvider: ResourceProvider
    @Inject lateinit var currencyAPIService: CurrencyAPIService

    private val _liveDataCurrency: MutableLiveData<DataCurrency> = MutableLiveData<DataCurrency>()
    val liveDataCurrency: LiveData<DataCurrency> = _liveDataCurrency

    private val _liveDataAllCurrency: MutableLiveData<List<DataCurrency>> = MutableLiveData<List<DataCurrency>>()
    val liveDataAllCurrency: LiveData<List<DataCurrency>> = _liveDataAllCurrency



    fun getListCells(): List<Cell>{
        return resourceProvider.getListCells()
    }

    fun getAllDataCurrency(){

        viewModelScope.launch {
            val allDataCurrency = currencyAPIService.getAllCurrency()
            for(currency in allDataCurrency){
                Log.d("TAG", currency.base)
            }
        }
    }

    fun getCertainCurrency(base: String){

        viewModelScope.launch{
            val currency = currencyAPIService.getCertainCurrency(base)
            _liveDataCurrency.value = currency
        }
    }

    fun updateData(){

    }
}