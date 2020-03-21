package ru.dmisb.test_app.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object FormatUtils {
    val currencyFormat = DecimalFormat("##,###,##0.00")

    init {
        val decimalFormatSymbols = DecimalFormatSymbols()
        decimalFormatSymbols.decimalSeparator = '.'
        decimalFormatSymbols.groupingSeparator= ' '

        currencyFormat.decimalFormatSymbols = decimalFormatSymbols
    }
}

fun Double?.toCurrencyText() : String {
    return if (this == null) ""
    else FormatUtils.currencyFormat.format(this) + "\u20BD"
}