package com.example.currency_exchange.model

import android.content.Context
import com.example.currency_exchange.R

class ResourceProvider(val context: Context) {

    fun getListCells():List<Cell>{

        val arrayAllCurency = context.resources.obtainTypedArray(R.array.arrayAllCurrency)
        val length = arrayAllCurency.length()
        var listCells: MutableList<Cell> = mutableListOf()

        for (i in 0 until length){
            val idCellInRes = arrayAllCurency.getResourceId(i,0)

            val cellInRes = context.resources.obtainTypedArray(idCellInRes)

            val base = cellInRes.getString(0)!!
            val name = cellInRes.getString(1)!!
            val drawable = cellInRes.getDrawable(2)!!

            listCells.add(Cell(base,name,drawable, null))
        }
        arrayAllCurency.recycle()
        return listCells
    }
}