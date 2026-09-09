package com.iftikar.memonto.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.iftikar.memonto.core.designsystem.input.MemontoOutlinedTextField
import com.iftikar.memonto.feature.home.impl.EditNoteAction
import com.iftikar.memonto.feature.home.impl.EditNoteState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun EditNoteComponent(
    state: EditNoteState,
    action: (EditNoteAction) -> Unit,
    noteId: Long,
    hazeState: HazeState
) {
    val shape = RoundedCornerShape(24.dp)

    Box(
        modifier = Modifier
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            )
            .fillMaxSize()
            .shadow(
                elevation = 12.dp,
                shape = shape,
                clip = true
            )
            .clip(shape)
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    tint = HazeTint(
                        MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.3f
                        )
                    ),
                    blurRadius = 16.dp
                )
            )
            .background(
                color = MaterialTheme.colorScheme.surface.copy(
                    alpha = 0.75f
                ),
                shape = shape
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 24.dp,
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 24.dp
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MemontoOutlinedTextField(
                value = state.title,
                singleLine = true,
                onValueChange = {
                    action(
                        EditNoteAction.OnTitleChange(it)
                    )
                },
                placeholder = if (state.title.isEmpty()) {
                    "Untitled Note"
                } else {
                    null
                }
            )

            MemontoOutlinedTextField(
                value = state.relatedTo,
                singleLine = true,
                onValueChange = {
                    action(
                        EditNoteAction.OnRelatedToChange(it)
                    )
                },
                placeholder = if (state.relatedTo.isEmpty()) {
                    "Hmm... What's this related to? (Optional)"
                } else {
                    null
                }
            )

            MemontoOutlinedTextField(
                value = state.body,
                modifier = Modifier.weight(1f),
                singleLine = false,
                minLines = 12,
                onValueChange = {
                    action(EditNoteAction.OnBodyChange(it))
                },
                placeholder = if (state.body.isEmpty()) {
                    "What you want to remember goes here..."
                } else {
                    null
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 58.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        action(EditNoteAction.OnCancel(noteId))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }

                Button(
                    onClick = {
                        action(EditNoteAction.OnSaveClick(noteId))
                    },
                    enabled = state.title.isNotEmpty() &&
                            state.body.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}