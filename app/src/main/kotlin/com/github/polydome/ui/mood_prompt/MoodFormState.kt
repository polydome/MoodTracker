package com.github.polydome.ui.mood_prompt

data class MoodFormState(
    val value: Int?,
    val emotions: List<Emotion>,
) {
    data class Emotion(
        val name: String,
        val selected: Boolean,
    )
}