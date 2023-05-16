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

class ViewModelMy: ViewModel() {

    @Inject lateinit var itemStateService: ItemStateService
    @Inject lateinit var apiService: APIService

    private val _listRates: MutableLiveData<List<Rate>> = MutableLiveData<List<Rate>>()
    val listRates: LiveData<List<Rate>> = _listRates

    private val _listItemStates: MutableLiveData<List<ItemState>> = MutableLiveData<List<ItemState>>()
    //val listItemStates: LiveData<List<ItemState>>

    private val _intt: MutableLiveData<Int> = MutableLiveData<Int>()
    val intt: LiveData<Int> = _intt

    init{
    ///getAllRates()
        //_listItemStates.postValue(itemStateService.getStates())
        /*viewModelScope.launch {
            apiService.getAllRates()
            //_listRates.value = apiService.getAllRates()
            //_listItemStates.postValue(itemStateService.getStates())
        }
*/
    }

    fun getAllRates(){

        viewModelScope.launch {
            val allRates = apiService.getAllRates()
            _listRates.postValue(allRates)
           /* for(currency in allRates){
                Log.d("TAG", currency.base)
            }*/
        }
    }

    fun getInt(){
        viewModelScope.launch {
            _intt.postValue(123)
        }
    }
}