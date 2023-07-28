package com.auction.mobile.tools

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class DateValueFormatter : ValueFormatter() {
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    override fun getFormattedValue(value: Float): String {
        // Преобразуйте значение типа float обратно в объект даты
        val timestamp = value.toLong()
        val date = Date(timestamp)
        // Верните отформатированную дату в виде строки
        return dateFormat.format(date)
    }
}
