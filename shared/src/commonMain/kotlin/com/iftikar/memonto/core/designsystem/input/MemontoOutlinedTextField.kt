package com.iftikar.memonto.core.designsystem.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
@Composable
fun MemontoOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    isError: Boolean = false,
    errorText: String? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
) {
    var isFocused by retain {
        mutableStateOf(false)
    }

    val shape = RoundedCornerShape(8.dp)

    val borderColor = when {
        !enabled ->
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

        isError ->
            MaterialTheme.colorScheme.error

        isFocused ->
            MaterialTheme.colorScheme.primary

        else ->
            MaterialTheme.colorScheme.outline
    }

    val borderWidth =
        if (isFocused || isError) 2.dp else 1.dp

    val backgroundColor =
        MaterialTheme.colorScheme.surface.copy(0.3f)

    val textColor = when {
        !enabled ->
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

        else ->
            MaterialTheme.colorScheme.onSurface
    }

    val labelColor = when {
        !enabled ->
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

        isError ->
            MaterialTheme.colorScheme.error

        isFocused ->
            MaterialTheme.colorScheme.primary

        else ->
            MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = textColor
                ),
                cursorBrush = SolidColor(
                    if (isError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(
                        color = backgroundColor,
                        shape = shape
                    )
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = shape
                    )
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (leadingIcon != null) {
                            leadingIcon()

                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )
                        }

                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            if (
                                value.isEmpty() &&
                                !isFocused &&
                                placeholder != null
                            ) {
                                Text(
                                    text = placeholder,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            innerTextField()
                        }

                        if (trailingIcon != null) {
                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )

                            trailingIcon()
                        }
                    }
                }
            )

            if (
                label != null &&
                (value.isNotEmpty() || isFocused)
            ) {
                Surface(
                    color = backgroundColor,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.TopStart)
                        .offset(y = (-8).dp)
                ) {
                    Text(
                        text = label,
                        color = labelColor,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(
                            horizontal = 4.dp
                        )
                    )
                }
            }
        }

        if (isError && errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 4.dp
                )
            )
        }
    }
}