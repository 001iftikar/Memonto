package com.iftikar.memonto.core.designsystem.component.bar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.iftikar.memonto.core.model.User
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun MemontoTopAppBar(
    isLoading: Boolean,
    greet: String,
    user: User?
) {
    TopAppBar(
        title = {
            if (isLoading) {
                GlitchSymbolText(greet = greet)
            } else {
                Text(
                    text = "$greet ${user?.name ?: "Your Grace"}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    )
}

@Composable
private fun GlitchSymbolText(
    modifier: Modifier = Modifier,
    greet: String,
    glitchLength: Int = 12 // Control how many symbols appear in the block
) {
    // 1. The character pool for the glitch effect
    val glitchSymbols =
        listOf('0', '1', '!', 'u', '#', '$', 's', '^', '&', 'e', '<', '>', 'r', '/', '|', '\\')

    // 2. Initialize the state with a random string of the specified length
    var displayedText by retain {
        mutableStateOf(
            CharArray(glitchLength) { glitchSymbols.random() }.concatToString()
        )
    }

    // 3. The infinite animation loop
    LaunchedEffect(glitchLength) {
        while (true) {
            // Generate a completely fresh array of random characters every frame
            displayedText = CharArray(glitchLength) { glitchSymbols.random() }.concatToString()

            delay(50.milliseconds)
        }
    }

    Text(
        text = "$greet $displayedText",
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleMedium,
        fontFamily = FontFamily.Monospace
    )
}







