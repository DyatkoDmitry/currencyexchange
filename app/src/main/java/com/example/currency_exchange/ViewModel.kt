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

    suspend fun updateItemsRate(position: Int){
        val updateItem = listItems.get(position)
        val remoteItem = itemService.getItem(updateItem.base)
        if(remoteItem != null){
            updateItem.rate = remoteItem.rate
        }
    }

    val itemFocusListener:ItemFocusListener = {

        val firstItem = listItems.removeAt(it)

        firstItem.rate = 0.0F

        listItems.add(0, firstItem)

        viewModelScope.launch{
            updateItemsRate(1)
            Log.d("TAG", "In FocusListener: listItems = ${listItems.hashCode()} and viewListItems = ${viewListItems.hashCode()}}")
            _currentListItems.postValue(listItems)
        }
    }

    fun setCoefficient(coefficient: Float){

        viewListItems.get(0).rate = coefficient

        for (i in 1 until listItems.size){
            viewListItems.get(i).rate = coefficient * listItems.get(i).rate
            //viewListItems.get(i).rate *= coefficient
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