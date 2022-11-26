package com.github.polydome.ui.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.polydome.ui.calendar.CalendarViewModel
import com.github.polydome.ui.calendar.MoodCalendar

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.padding(64.dp)) {
            val openDialog = remember { mutableStateOf(false) }

            MoodCalendar(CalendarViewModel())


            Box(Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("How are you?", fontSize = 32.sp, fontWeight = FontWeight.Light)
                    IconButton(
                        onClick = {
                            openDialog.value = true
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