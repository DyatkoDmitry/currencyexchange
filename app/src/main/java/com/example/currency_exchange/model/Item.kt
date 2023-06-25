package com.example.currency_exchange.model

import android.graphics.drawable.Drawable

data class Item (val base: String, val name: String, val drawable: Drawable, var rate: Float){
   /* public fun clone(): Any {
        //var item = super.clone() as Item
        val clonedItem = Item(base, name, drawable, rate)
        return clonedItem
    }*/

    fun deepCopy(
        base: String = this.base,
        name: String = this.name,
        drawable: Drawable = this.drawable,
        rate: Float = this.rate) = Item(base, name, drawable, rate).copy()

    fun cop(): Item{
        return this.copy(rate = this.rate)
    }

}