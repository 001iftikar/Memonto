package com.iftikar.memonto.core.designsystem.component.bar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.iftikar.memonto.core.designsystem.component.glitch.GlitchSymbolText
import com.iftikar.memonto.core.model.User

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







