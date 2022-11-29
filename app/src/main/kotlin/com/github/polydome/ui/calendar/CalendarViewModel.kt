package com.github.polydome.ui.calendar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalendarViewModel {
    private val _state = MutableStateFlow(CalendarState(
        (0 .. 4).map { week ->
            (week * 7 .. week * 7 + 6).map { day ->
                null
            }.toTypedArray()
        }
    ))
    val state: StateFlow<CalendarState> = _state.asStateFlow()
}