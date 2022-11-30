package com.github.polydome.ui.event

interface Sink<T> {
    fun emit(event: T)
}