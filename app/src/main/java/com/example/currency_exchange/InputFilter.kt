package com.example.currency_exchange

import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import java.util.regex.Matcher
import java.util.regex.Pattern

class InputFilterEditText: InputFilter {

   /* var mPattern: Pattern

    init {
        mPattern = Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }*/

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
}