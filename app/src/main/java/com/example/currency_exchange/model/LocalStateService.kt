package com.example.currency_exchange.model

import android.content.Context
import com.example.currency_exchange.R

class LocalStateService(val context: Context) {

    fun getLocalStates():List<LocalState>{

        val mainArray = context.resources.obtainTypedArray(R.array.arrayAllCurrency)
        val length = mainArray.length()
        var stateList: MutableList<LocalState> = mutableListOf()

        for (i in 0 until length){
            val resId = mainArray.getResourceId(i,0)
            val state = context.resources.obtainTypedArray(resId)

            val base = state.getString(0)!!
            val name = state.getString(1)!!
            val drawable = state.getDrawable(2)!!

            stateList.add(LocalState(base,name,drawable))
        }

        mainArray.recycle()
        return stateList
    }
}