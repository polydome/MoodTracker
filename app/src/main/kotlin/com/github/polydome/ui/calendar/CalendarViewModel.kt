package com.github.polydome.ui.calendar

import com.github.polydome.usecase.GetScoresBreakdown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    getScoresBreakdown: GetScoresBreakdown
) {
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
            val breakdown = getScoresBreakdown.execute(date.year, date.monthValue)
            val firstDayOfMonth = date.withDayOfMonth(1)
            val lastDayOfMonth = date.month.length(date.isLeapYear)
            val skippedDays = firstDayOfMonth.dayOfWeek.ordinal
            val weeks = (0 .. 4).map { week ->
                (week * 7 .. week * 7 + 6).map { cell ->
                    if (cell < skippedDays) null
                    else if (cell > lastDayOfMonth) null
                    else {
                        val dayNumber = cell - skippedDays + 1
                        val value = breakdown[dayNumber]?.let {
                            if (it == 0) null
                            else it
                        }
                        CalendarState.Day(
                            number = dayNumber,
                            value = value
                        )
                    }
                }.toTypedArray()
            }
            _state.emit(state.value.copy(weeks = weeks))
        }
    }
}