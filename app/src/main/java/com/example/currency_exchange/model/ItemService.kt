package com.example.currency_exchange.model

import javax.inject.Inject

class ItemService @Inject constructor(localStateService: LocalStateService, val apiService: APIService) {

    val listLocalStates = localStateService.getLocalStates()

    val length = listLocalStates.size

    suspend fun getItems():MutableList<Item>{

        var listItems:MutableList<Item> = mutableListOf()

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
                    //rate = setValidRate(remoteState.rate)
                }
            }
            listItems.add(i, Item(base,name,drawable,rate))
        }
        return listItems
    }

    private fun setValidRate(rate: Float): Float{
        val(integerPart, fractionalPart) = rate.toString().split(".")
        if(fractionalPart.length <= 5) return rate

        val fractionalRate = fractionalPart.substring(0,5)
        return (integerPart + "." + fractionalRate).toFloat()
    }

    suspend fun getItem(base: String): Item?{

        val remoteCertainState = apiService.getCertainRemoteState(base)
        var item: Item? = null

        for(localState in listLocalStates){
            if(localState.base.equals(base)){
               //val rate=setValidRate(remoteCertainState.rate)
               val rate=remoteCertainState.rate
               val base=localState.base
               val drawable = localState.drawable
               val name = localState.name
               item = Item(base,name,drawable,rate)
           }
        }
        return item
    }
}