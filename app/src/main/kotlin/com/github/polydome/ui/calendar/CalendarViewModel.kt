package com.github.polydome.ui.calendar

import com.github.polydome.ui.event.DataEvent
import com.github.polydome.usecase.GetScoresBreakdown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val getScoresBreakdown: GetScoresBreakdown,
    private val dataEvents: Flow<DataEvent>
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _state = MutableStateFlow(CalendarState(
        (0..4).map { week ->
            (week * 7..week * 7 + 6).map { _ ->
                null
            }.toTypedArray()
        }
    ))
    val state: StateFlow<CalendarState> = _state.asStateFlow()

    init {
        coroutineScope.launch {
            update()

            dataEvents.collect {
                update()
            }
        }
    }

    private suspend fun update() {
        val date = LocalDate.now()
        val breakdown = getScoresBreakdown.execute(date.year, date.monthValue)
        val firstDayOfMonth = date.withDayOfMonth(1)
        val lastDayOfMonth = date.month.length(date.isLeapYear)
        val skippedDays = firstDayOfMonth.dayOfWeek.ordinal
        val weeks = (0..4).map { week ->
            (week * 7..week * 7 + 6).map { cell ->
                if (cell < skippedDays) null
                else if (cell > lastDayOfMonth) null
                else {
                    val dayNumber = cell - skippedDays + 1
                    CalendarState.Day(
                        number = dayNumber,
                        emotions = breakdown[dayNumber]?.emotions ?: emptyList(),
                        lastMood = breakdown[dayNumber]?.lastScore?.let {
                            if (it == 0) null
                            else it
                        },
                        lowestMood = breakdown[dayNumber]?.minScore,
                        highestMood = breakdown[dayNumber]?.maxScore,
                        averageMood = breakdown[dayNumber]?.avgScore
                    )
                }
            }.toTypedArray()
        }
        _state.emit(state.value.copy(weeks = weeks))
    }

}