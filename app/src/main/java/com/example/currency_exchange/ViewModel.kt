package com.example.currency_exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.Item
import com.example.currency_exchange.model.ItemService
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelMy(val apiService: APIService, val itemService: ItemService): ViewModel() {

    private val _currentListItems: MutableLiveData<MutableList<Item>> = MutableLiveData<MutableList<Item>>()
    val currentListItems: LiveData<MutableList<Item>> = _currentListItems

    private lateinit var listItems: MutableList<Item>

    private lateinit var viewListItems: MutableList<Item>

    private lateinit var copiedListItems: MutableList<Item>

    private var coefficientFirstRate: Float? = null

    suspend fun updateItemsRate(position: Int){
        val updateItem = listItems.get(position)
        val remoteItem = itemService.getItem(updateItem.base)
        if(remoteItem != null){
            updateItem.rate = remoteItem.rate
        }
    }

    val itemFocusListener:ItemFocusListener = {

        val firstItem = listItems.removeAt(it)

        coefficientFirstRate = 0F
        firstItem.rate = 0.0F

        listItems.add(0, firstItem)

        viewModelScope.launch{
            updateItemsRate(1)
            _currentListItems.postValue(listItems)
        }
    }

     fun setCoefficient(coefficient: Float){

         coefficientFirstRate = coefficient

         viewListItems = mutableListOf()

         Log.d("TAG", "listItem(1) = ${listItems.get(1).rate.toString()}")
         for (item in listItems){
             viewListItems.add(item.copy(item.base, item.name, item.drawable, item.rate))
         }

         ////
         viewModelScope.launch {
             updateNewItems(viewListItems)
             //updateNewItems(copiedListItems)
         }
         ////


        //_currentListItems.postValue(viewListItems)
    }

    suspend fun getItems(): MutableList<Item>{

        if(!this::listItems.isInitialized){
            listItems = itemService.getItems()
        }
        return listItems
    }

    suspend fun startRefreshingRates(){
        var isRefreshing: Boolean = true
        while(isRefreshing){
            delay(6000)

            val newItems = itemService.getItems()

            updateNewItems(newItems)

            //isRefreshing = false
        }
    }

    suspend fun updateNewItems(newItems: MutableList<Item>){

        //viewListItems = mutableListOf()

        for(item in listItems) {
            for (newItem in newItems) {
                if (item.base == newItem.base) {
                    item.rate = newItem.rate
                }
            }
        }
        coefficientFirstRate?.let{
            if(it > 0){
                for(item in listItems){
                    item.rate *= it
                }
            }
        }

        coefficientFirstRate?.let {
            listItems.get(0).rate = it
        }

        _currentListItems.postValue(listItems)
            //_currentListItems.postValue(viewListItems)
    }

    fun pushItToListItems(newList: MutableList<Item>){

        for(item in listItems) {
            for (newItem in newList) {
                if (item.base == newItem.base) {
                    item.rate = newItem.rate
                }
            }
        }
    }

}