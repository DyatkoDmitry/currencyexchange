import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


class DecimalDigitsInputFilter(digitsAfterZero: Int) : InputFilter {
    var mPattern: Pattern

    init {
        mPattern = Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): String? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }
}






/*import android.text.InputFilter
import android.text.Spanned

class NumberInputFilter(private val digsBeforeDot: Int, private val digsAfterDot: Int) :
    InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): CharSequence? {
        var isValid = true

        // Получаем значение
        val newText = StringBuilder(dest).replace(dstart, dend, source.toString())
        val size = newText.length
        var decInd = -1 // индекс десятичного разделителя
        // проверяем десятичный разделитель
        // количество разделителей не должно превышать 1
        for (i in 0 until size) {
            if (newText[i] == DECIMAL_DELIMITER) {
                if (decInd < 0) {
                    decInd = i // запоминаем индекс разделителя
                } else { // разделителей более 1, некорректный ввод
                    isValid = false
                    break
                }
            }
        }

        // проверяем длину числа
        if (decInd < 0) { // случай когда разделителя нет
            if (size > digsBeforeDot) { // проверяем длину всего числа
                isValid = false
            }
        } else if (decInd > digsBeforeDot) { // проверяем длину целой части
            isValid = false
        } else if (size - decInd - 1 > digsAfterDot) { // проверяем длину дробной части
            isValid = false
        }
        return if (isValid) {
            null
        } else if (source == "") {
            dest.subSequence(dstart, dend)
        } else {
            source
        }
    }

    companion object {
        const val DECIMAL_DELIMITER = '.'
    }
}*/









/*
package com.example.currency_exchange

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int):InputFilter {
    var mPattern: Pattern

    init {
        mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): CharSequence {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) source else "000"
    }
}*/
