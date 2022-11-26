import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CalendarState(
    val lastDayOfMonth: Int = 30,
    val dayToMoodScore: Map<Int, Int> = mapOf(
        5 to 1,
        7 to 2,
        14 to 3,
        16 to 4,
        23 to 5
    )
)

const val HUE_LOWEST = 0f
const val HUE_HIGHEST = 100f
const val SATURATION_BASE = 1f
const val LIGHTNESS_BASE = 0.5f
const val SCORE_LOWEST = 1
const val SCORE_HIGHEST = 5
class CalendarViewModel {
    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state.asStateFlow()


}
@Composable
@Preview
fun MoodCalendar(viewModel: CalendarViewModel) {
    val state by viewModel.state.collectAsState()
    val numbers = (1 .. state.lastDayOfMonth).toList()

    Box(Modifier.width(60.dp*7)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            items(numbers.size) {
                Column {
                    val score = state.dayToMoodScore[it]
                    Box(modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .background(color = score?.let { getMoodScoreColor(it) } ?: Color.Transparent)
                        .border(1.dp, Color.Black, RectangleShape)) {
                        Text(text = "  ${numbers[it]}", modifier = Modifier.align(Alignment.TopEnd))
                        score?.let { score ->
                            Text(text = "$score", modifier = Modifier.align(Alignment.BottomStart))
                        }
                    }
                }
            }
        }
    }
}

private fun getMoodScoreColor(score: Int): Color {
    val hue = (((HUE_HIGHEST - HUE_LOWEST) * (score - SCORE_LOWEST)) / (SCORE_HIGHEST - SCORE_LOWEST)) + HUE_LOWEST
    return Color.hsl(hue, SATURATION_BASE, LIGHTNESS_BASE)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false)  }

            Button(onClick = {
                openDialog.value = true
            }) {
                Text("Click me")
            }

            MoodCalendar(CalendarViewModel())
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Dialog Title")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Close")
                        }
                    }
                )
            }
        }

    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
