package com.iftikar.memonto.feature.note_details.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iftikar.memonto.feature.note_details.component.EditNoteFab
import com.iftikar.memonto.feature.note_details.component.NoteBody
import com.iftikar.memonto.feature.note_details.component.RelatedToChip

@Composable
fun NoteDetailsScreen(
    noteId: Long,
    noteDetailsViewModel: NoteDetailsViewModel,
    onEditClick: (Long) -> Unit
) {
    val state by noteDetailsViewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    LaunchedEffect(true) {
        noteDetailsViewModel.getNoteById(noteId)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(state = scrollState)
                .padding(horizontal = 24.dp)
        ) {
            if (state.error != null) {
                Text(
                    text = state.error ?: "Oops! Something went wrong",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(13.dp))
                Button(
                    onClick = { noteDetailsViewModel.getNoteById(noteId) }
                ) {
                    Text(
                        text = "Retry"
                    )
                }
            }
            if (state.error == null && state.note != null) {
                state.note?.relationTo?.let {
                    RelatedToChip(
                        text = it
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Text(
                    text = state.note?.title ?: "Data corrupted",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 38.sp,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                NoteBody(
                    text = state.note?.body ?: "Data corrupted",
                    borderColor = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        state.note?.let {
            EditNoteFab(
                modifier = Modifier.padding(end = 34.dp, bottom = 34.dp),
                onClick = { onEditClick(it.id) }
            )
        }
    }
}

