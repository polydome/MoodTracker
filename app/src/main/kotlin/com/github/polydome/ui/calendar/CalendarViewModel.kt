package com.github.polydome.ui.calendar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel {
    private val _state = MutableStateFlow(CalendarState(
        (0 .. 4).map { week ->
            (week * 7 .. week * 7 + 6).map { _ ->
                null
            }.toTypedArray()
        }
    ))
    val state: StateFlow<CalendarState> = _state.asStateFlow()

    init {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val date = LocalDate.now()
            val firstDayOfMonth = date.withDayOfMonth(1)
            val lastDayOfMonth = date.month.length(date.isLeapYear)
            val skippedDays = firstDayOfMonth.dayOfWeek.ordinal
            val weeks = (0 .. 4).map { week ->
                (week * 7 .. week * 7 + 6).map { cell ->
                    if (cell < skippedDays) null
                    else if (cell > lastDayOfMonth) null
                    else CalendarState.Day(
                        number = cell - skippedDays + 1,
                        value = null
                    )
                }.toTypedArray()
            }
            _state.emit(state.value.copy(weeks = weeks))
        }
    }
}