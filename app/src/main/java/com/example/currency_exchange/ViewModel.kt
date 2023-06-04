package com.example.currency_exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.Item
import com.example.currency_exchange.model.ItemService
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelMy(val apiService: APIService, val itemService: ItemService): ViewModel() {

    private val _currentListItems: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()
    val currentListItems: LiveData<List<Item>> = _currentListItems

    private lateinit var listItems: MutableList<Item>

    init{
        viewModelScope.launch {
            getItems()
        }
    }

    suspend fun getItems():MutableList<Item>{
        listItems = itemService.getItems()
        _currentListItems.postValue(listItems)
        return listItems
    }

    suspend fun updateItem(position: Int){
        val updateItem = listItems.get(position)
        val remoteItem = itemService.getItem(updateItem.base)
        if(remoteItem != null){
            updateItem.rate = remoteItem.rate
        }
    }

    val itemInputListener:ItemInputListener = {
        Log.d("TAG", "editable = " + "${it}")
    }

    val itemFocusListener:ItemFocusListener = {

        val firstItem = listItems.removeAt(it)
        firstItem.rate = 0.0F
        listItems.add(0, firstItem)

        viewModelScope.launch{
            updateItem(1)
            _currentListItems.postValue(listItems)
        }
    }
}