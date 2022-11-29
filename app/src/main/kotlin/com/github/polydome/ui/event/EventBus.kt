package com.github.polydome.ui.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class EventBus<T> {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val _events = MutableSharedFlow<T>()

    val events: Flow<T> = _events
    val sink = object : Sink<T> {
        override fun emit(event: T) {
            coroutineScope.launch {
                _events.emit(event)
            }
        }
    }
}
