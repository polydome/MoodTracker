package com.github.polydome.ui.mood_prompt

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoodFormViewModel {
    private val _state = MutableStateFlow(
        MoodFormState(
            emotions = placeholderEmotions(),
            value = null
        )
    )
    val state: StateFlow<MoodFormState> = _state.asStateFlow()

    fun selectMoodValue(value: Int) {
        _state.value = _state.value.copy(
            value = value
        )
    }

    fun submitPrompt() {
        println("Submitting prompt with value = ${_state.value.value}")
    }

    fun toggleEmotion(emotionIndex: Int) {
        _state.value = _state.value.copy(
            emotions = _state.value.emotions.mapIndexed { index, emotion ->
                if (emotionIndex == index)
                    emotion.copy(selected = !emotion.selected)
                else
                    emotion
            }
        )
    }

    fun addEmotion(emotionName: String) {
        _state.value = _state.value.copy(
            emotions = _state.value.emotions + MoodFormState.Emotion(
                name = emotionName,
                selected = false
            )
        )
    }
}

private fun placeholderEmotions() = listOf(
    MoodFormState.Emotion("Anger", false),
    MoodFormState.Emotion("Happiness", false),
    MoodFormState.Emotion("Sadness", false),
    MoodFormState.Emotion("Richness", false),
)