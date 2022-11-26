import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.polydome.ui.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
