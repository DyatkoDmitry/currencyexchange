package com.example.currency_exchange.model

import javax.inject.Inject

class ItemService @Inject constructor(val localStateService: LocalStateService, val apiService: APIService) {

    val listLocalStates = localStateService.getLocalStates()

    suspend fun getItems():List<Item>{

        var listItems:MutableList<Item> = mutableListOf()

        val length = listLocalStates.size

        val listRemoteStates = apiService.getAllRemoteStates()

        for (i in 0 until length){
            val localState = listLocalStates.get(i)
            val base = localState.base
            val name = localState.name
            val drawable = localState.drawable
            var rate = 0F

            for(remoteState in listRemoteStates){
                if(remoteState.base.equals(base)){
                    rate = remoteState.rate
                }
            }
            listItems.add(i, Item(base,name,drawable,rate))
        }
        return listItems
    }


}