package com.example.currency_exchange

import android.text.InputFilter
import android.text.Spanned
import android.util.Log

class InputFilterEditText: InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): CharSequence? {

        if(!dest.isEmpty()) return source

        val regex = ("[0-9]+((\\.[0-9]{0,5})?)||(\\.)?").toRegex()
        val matRes = regex.find(source)

        matRes?.let {
            return matRes.value
        } ?: return source
    }
}





/*
class InputFilterEditText: InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): CharSequence? {
        if(!dest.isEmpty()) return source

        val (intOfSource, fracOfSource) = source.toString().split('.')

        if(fracOfSource.length <= 5){
            return source
        }

        val frac = fracOfSource.substring(0,5)
        return (intOfSource + "." + frac)
    }
}*/
