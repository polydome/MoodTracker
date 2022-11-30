package com.github.polydome.ui.calendar

data class CalendarState(
    val weeks: List<Array<Day?>>
) {
    data class Day(
        val number: Int,
        val value: Int?,
        val emotions: List<String>
    )
}