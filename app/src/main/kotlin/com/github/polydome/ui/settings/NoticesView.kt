package com.github.polydome.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun NoticesView(modifier: Modifier = Modifier, newNotices: Flow<String>) {
    val visibleNotices = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        newNotices.collect {
            visibleNotices.add(it)
            launch(Dispatchers.Default) {
                delay(5.seconds)
                visibleNotices.remove(it)
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        for (notice in visibleNotices) {
            item {
                Notice(text = notice, onClick = {
                    visibleNotices.remove(notice)
                })
            }

        }
    }
}