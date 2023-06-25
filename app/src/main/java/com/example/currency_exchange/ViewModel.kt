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

    init{
        viewModelScope.launch {
            //getItems()
            //_currentListItems.postValue(listItems)
        }
    }

    /*suspend fun getItems():MutableList<Item>{
        listItems = itemService.getItems()

        return listItems
    }*/

    suspend fun getViewItems():MutableList<Item>{

        if(!this::listItems.isInitialized){
            listItems = itemService.getItems()
        }

        viewListItems = mutableListOf()

        for (item in listItems){
            viewListItems.add(item.copy(item.base, item.name, item.drawable, item.rate))
        }

        Log.d("TAG", "listItems = ${listItems.hashCode()} and viewListItems = ${viewListItems.hashCode() }")
        return viewListItems
    }


    suspend fun updateItem(position: Int){
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
            updateItem(1)
            _currentListItems.postValue(viewListItems)
        }

        /*val firstItem = listItems.removeAt(it)

        firstItem.rate = 0.0F

        listItems.add(0, firstItem)

        viewModelScope.launch{
            updateItem(1)
            _currentListItems.postValue(listItems)
        }*/
    }

    fun setCoefficient(coefficient: Float){

        //Log.d("TAG", "listItems(2) = ${listItems.get(2).rate.toString()}")

        //viewListItems = mutableListOf<Item>().apply { addAll(listItems) }

       // Log.d("TAG", "viewListItems(2) = ${viewListItems.get(2).rate.toString()}")

        viewListItems.get(0).rate = coefficient

        for (i in 1 until viewListItems.size){
            viewListItems.get(i).rate *= coefficient
        }
        //Log.d("TAG", "listItemsAfterCoefficient(2) = ${listItemsAfterCoefficient.get(2).rate.toString()} and hash =${listItemsAfterCoefficient.hashCode()}")
        _currentListItems.postValue(viewListItems)

    }
}