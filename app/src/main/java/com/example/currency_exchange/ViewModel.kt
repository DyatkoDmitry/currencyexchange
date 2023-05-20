package com.example.currency_exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.LocalState
import com.example.currency_exchange.model.LocalStateService
import com.example.currency_exchange.model.RemoteState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import javax.inject.Inject

class ViewModelMy(val itemStateService: LocalStateService, val apiService: APIService): ViewModel() {

    private val _listRates: MutableLiveData<List<RemoteState>> = MutableLiveData<List<RemoteState>>()
    val listRates: LiveData<List<RemoteState>> = _listRates

    private val _listItemStates: MutableLiveData<List<LocalState>> = MutableLiveData<List<LocalState>>()
    val listItemStates: LiveData<List<LocalState>> = _listItemStates

    init{
        getAllRates()
        getAllStates()
    }

    fun getAllRates(){
        viewModelScope.launch {
            val allRates = apiService.getAllRemoteStates()
            _listRates.postValue(allRates)
        }
    }

    fun getAllStates(){
        viewModelScope.launch {
            val allStates = itemStateService.getLocalStates()
            _listItemStates.postValue(allStates)
        }
    }
}