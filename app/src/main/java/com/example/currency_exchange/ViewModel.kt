package com.example.currency_exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_exchange.model.Item
import com.example.currency_exchange.model.ItemService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelMy(val itemService: ItemService): ViewModel() {

    private val _currentListItems: MutableLiveData<MutableList<Item>> = MutableLiveData<MutableList<Item>>()
    val currentListItems: LiveData<MutableList<Item>> = _currentListItems

    private lateinit var listItems: MutableList<Item>

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

         viewModelScope.launch {
             recycleList()
         }
    }

    suspend fun getItems(): MutableList<Item>{

        if(!this::listItems.isInitialized){
            listItems = itemService.getItems()
        }
        return listItems
    }

    suspend fun startRefreshingRates(){

        while(true){
            delay(5000)

            val newList = itemService.getItems()

            updateRates(newList)

            recycleList()
        }
    }

    suspend fun recycleList(){

        var viewListItems: MutableList<Item> = mutableListOf()

        for (item in listItems){
            viewListItems.add(item.copy(item.base, item.name, item.drawable, item.rate))
        }

        coefficientFirstRate?.let{
            if(it > 0){
                for(viewItem in viewListItems){
                    viewItem.rate *= it
                }
            }
        }

        coefficientFirstRate?.let {
            viewListItems.get(0).rate = it
        }

        _currentListItems.postValue(viewListItems)
    }

    fun updateRates(newList: MutableList<Item>){

        for(item in listItems) {
            for (newItem in newList) {
                if (item.base == newItem.base) {
                    item.rate = newItem.rate
                }
            }
        }
    }
}