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

    private val _currentListItems: MutableLiveData<MutableList<Item>> = MutableLiveData<MutableList<Item>>()
    val currentListItems: LiveData<MutableList<Item>> = _currentListItems

    private lateinit var listItems: MutableList<Item>

    private lateinit var viewListItems: MutableList<Item>

    suspend fun getOriginViewItems():MutableList<Item>{

        if(!this::listItems.isInitialized){
            listItems = itemService.getItems()
        }

        viewListItems = mutableListOf()

        for (item in listItems){
            viewListItems.add(item.copy(item.base, item.name, item.drawable, item.rate))
        }

        return viewListItems

    }

    suspend fun updateItemsRate(position: Int){
        val updateItem = viewListItems.get(position)
        val remoteItem = itemService.getItem(updateItem.base)
        if(remoteItem != null){
            updateItem.rate = remoteItem.rate
        }
    }

    val itemInputListener:ItemInputListener = {

    }

    val itemFocusListener:ItemFocusListener = {

        val firstItem = viewListItems.removeAt(it)

        firstItem.rate = 0.0F

        viewListItems.add(0, firstItem)

        viewModelScope.launch{
            updateItemsRate(1)
            Log.d("TAG", "In FocusListener: listItems = ${listItems.hashCode()} and viewListItems = ${viewListItems.hashCode()}}")
            _currentListItems.postValue(viewListItems)
        }
    }

    fun setCoefficient(coefficient: Float){

        viewListItems.get(0).rate = coefficient

        for (i in 1 until viewListItems.size){
            viewListItems.get(i).rate *= coefficient
        }
        _currentListItems.postValue(viewListItems)
    }

    suspend fun setInitializedLists(){
        if(!this::listItems.isInitialized){
            listItems = itemService.getItems()
        }

        viewListItems = mutableListOf()

        for (item in listItems){
            viewListItems.add(item.copy(item.base, item.name, item.drawable, item.rate))
        }
    }

    fun getViewItems(): MutableList<Item>{
        return viewListItems
    }
}