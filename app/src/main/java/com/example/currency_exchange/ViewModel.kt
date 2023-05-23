package com.example.currency_exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.Item
import com.example.currency_exchange.model.ItemService
import com.example.currency_exchange.model.LocalState
import com.example.currency_exchange.model.LocalStateService
import com.example.currency_exchange.model.RemoteState
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import javax.inject.Inject

class ViewModelMy(val apiService: APIService, val itemService: ItemService): ViewModel() {

    private val _listItems: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()
    val listItems: LiveData<List<Item>> = _listItems

    init{
        viewModelScope.launch {
            getItems()
            /*while (true){
                delay(10000)
                //getItems()
            }*/
        }
    }

    fun getItems(){
        viewModelScope.launch {
            val listItems = itemService.getItems()
            _listItems.postValue(listItems)
        }
    }
}