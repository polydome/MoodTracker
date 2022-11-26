package com.github.polydome.ui.app

import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.polydome.ui.calendar.CalendarViewModel
import com.github.polydome.ui.calendar.MoodCalendar

enum class Tab {
    Button,
    Prompt
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.padding(64.dp)) {
            val openDialog = remember { mutableStateOf(false) }
            var tab by remember { mutableStateOf(Tab.Button) }

            MoodCalendar(CalendarViewModel())


            Box {
                Crossfade(targetState = tab, modifier = Modifier.align(Alignment.Center)) { currentTab ->
                    when (currentTab) {
                        Tab.Button -> ButtonView(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize(),
                            switchTab = {
                                tab = Tab.Prompt
                            }
                        )

                        Tab.Prompt -> PromptView(switchTab = {
                            tab = Tab.Button
                        })
                    }
                }
            }



            if (openDialog.value) {
                AlertDialog(onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                }, title = {
                    Text(text = "Dialog Title")
                }, text = {
                    Text("Here is a text ")
                }, confirmButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("Add")
                    }
                }, dismissButton = {
                    Button(onClick = {
                        openDialog.value = false
                    }) {
                        Text("Close")
                    }
                })
            }
        }

    }
}

@Composable
fun ButtonView(modifier: Modifier = Modifier, switchTab: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("How are you?", fontSize = 32.sp, fontWeight = FontWeight.Light)
        IconButton(
            onClick = {
                switchTab()
            },
            modifier = Modifier
                .size(32.dp)
        ) {
            Icon(
                Icons.Filled.Add, "contentDescription", Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun PromptView(modifier: Modifier = Modifier, switchTab: () -> Unit) {
    Column(modifier = modifier) {
        Text("The form here")
        Button(onClick = switchTab) {
            Text("Back")
        }
    }
}