package com.github.polydome.ui.mood_prompt

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoodPromptViewModel {
    private val _state = MutableStateFlow(MoodPromptState(
        emotions = emptyList(),
        value = null
    ))
    val state: StateFlow<MoodPromptState> = _state.asStateFlow()

    fun selectMoodValue(value: Int) {
        _state.value = _state.value.copy(
            value = value
        )
    }

    fun submitPrompt() {
        println("Submitting prompt with value = ${_state.value.value}")
    }
}