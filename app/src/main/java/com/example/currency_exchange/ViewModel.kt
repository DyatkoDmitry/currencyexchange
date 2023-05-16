package com.example.currency_exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.ItemState
import com.example.currency_exchange.model.ItemStateService
import com.example.currency_exchange.model.Rate
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import javax.inject.Inject

class ViewModelMy(val itemStateService: ItemStateService,val apiService: APIService): ViewModel() {

    //@Inject lateinit var itemStateService: ItemStateService
    //@Inject lateinit var apiService: APIService

    private val _listRates: MutableLiveData<List<Rate>> = MutableLiveData<List<Rate>>()
    val listRates: LiveData<List<Rate>> = _listRates

    private val _listItemStates: MutableLiveData<List<ItemState>> = MutableLiveData<List<ItemState>>()
    val listItemStates: LiveData<List<ItemState>> = _listItemStates

    init{
        getAllRates()
    }

    fun getAllRates(){
        viewModelScope.launch {
            val allRates = apiService.getAllRates()
            _listRates.postValue(allRates)
        }
    }
}