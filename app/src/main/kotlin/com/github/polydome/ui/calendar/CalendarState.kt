package com.github.polydome.ui.calendar

data class CalendarState(
    val weeks: List<Array<Day?>>
) {
    data class Day(
        val number: Int,
        val emotions: List<String>,
        val lowestMood: Int?,
        val highestMood: Int?,
        val averageMood: Int?,
        val lastMood: Int?
    )
}