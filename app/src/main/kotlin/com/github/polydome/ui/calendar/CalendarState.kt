package com.github.polydome.ui.calendar

data class CalendarState(
    val lastDayOfMonth: Int = 30,
    val dayToMoodScore: Map<Int, Int> = mapOf(
        5 to 1,
        7 to 2,
        14 to 3,
        16 to 4,
        23 to 5
    )
)