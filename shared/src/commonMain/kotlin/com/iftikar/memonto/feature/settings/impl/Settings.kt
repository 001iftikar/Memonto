package com.iftikar.memonto.feature.settings.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iftikar.memonto.core.designsystem.component.glitch.GlitchSymbolText
import com.iftikar.memonto.core.designsystem.theme.LocalSpacing
import com.iftikar.memonto.feature.settings.components.ThemeToggleComponent

@Composable
fun Settings(
    isOnDarkTheme: Boolean?,
    listState: LazyListState,
    viewModel: SettingsViewModel,
    isUsernameFinding: Boolean,
    userName: String?,
    onUserNameSave: (String) -> Unit
) {
    val spacing = LocalSpacing.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action = viewModel::onAction
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(userName) {
        viewModel.setUserName(userName ?: "")
    }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.screenHorizontalPadding)
    ) {
        item {
            if (isUsernameFinding) {
                GlitchSymbolText(greet = null)
            } else {
                BasicTextField(
                    value = state.userName,
                    onValueChange = { action(SettingsScreenAction.OnUserNameChange(it)) },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (state.userName.isEmpty()) {
                            Text(
                                text = "Click here to set you name",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        innerTextField()
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (state.userName.isEmpty()) {
                                keyboardController?.hide()
                                focusManager.clearFocus(true)
                                return@KeyboardActions
                            }
                            onUserNameSave(state.userName)
                            keyboardController?.hide()
                            focusManager.clearFocus(true)
                        }
                    )
                )
            }
        }

        item {
            ThemeToggleComponent(
                isDarkTheme = isOnDarkTheme ?: false,
                toggleTheme = { action(SettingsScreenAction.OnThemeToggle) }
            )
        }
    }
}