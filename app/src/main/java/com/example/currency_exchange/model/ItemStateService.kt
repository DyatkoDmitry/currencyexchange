package com.example.currency_exchange.model

import android.content.Context
import com.example.currency_exchange.R

class ItemStateService(val context: Context) {

    fun getStates():List<ItemState>{

        val mainArray = context.resources.obtainTypedArray(R.array.arrayAllCurrency)
        val length = mainArray.length()
        var stateList: MutableList<ItemState> = mutableListOf()

        for (i in 0 until length){
            val resId = mainArray.getResourceId(i,0)
            val state = context.resources.obtainTypedArray(resId)

            val base = state.getString(0)!!
            val name = state.getString(1)!!
            val drawable = state.getDrawable(2)!!

            stateList.add(ItemState(i,base,name,drawable,null))
        }

        mainArray.recycle()
        return stateList
    }
}