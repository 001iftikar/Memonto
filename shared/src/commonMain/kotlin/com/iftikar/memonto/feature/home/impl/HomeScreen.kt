package com.iftikar.memonto.feature.home.impl

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.iftikar.memonto.core.designsystem.component.card.NoteCard
import com.iftikar.memonto.core.designsystem.theme.LocalSpacing
import com.iftikar.memonto.core.util.formatRelativeTime
import com.iftikar.memonto.feature.home.components.EditNoteComponent
import memonto.shared.generated.resources.Res

@Composable
fun HomeScreen(
    listState: LazyListState,
    viewModel: HomeViewModel,
    showError: (String) -> Unit
) {
    val spacing = LocalSpacing.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val editNoteState by viewModel.editNoteState.collectAsStateWithLifecycle()
    val currentTimeMillis by viewModel.currentTime.collectAsStateWithLifecycle()
    val action = viewModel::onAction

    LaunchedEffect(true) {
        viewModel.event.collect { event ->
            when (event) {
                is HomeScreenEvent.ShowError -> {
                    showError(event.error)
                }
            }
        }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.screenHorizontalPadding)
    ) {
        if (state.isLoading) {
            item {
                Column(
                    modifier = Modifier.fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            if (state.error != null) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error ?: "Oops! Something went wrong.",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            } else {
                if (state.notes.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = Res.getUri("drawable/Empty_Notes.png"),
                                contentDescription = "Empty List, press plus to add notes",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    items(items = state.notes, key = { it.id }) { note ->
                        val timeStampText = retain(note.updatedAt, currentTimeMillis) {
                            formatRelativeTime(note.updatedAt, currentTimeMillis)
                        }
                        NoteCard(
                            note = note,
                            timeStampText = timeStampText,
                            longPressedVisible = state.longPressedNotes.contains(note.id),
                            onLongPressed = { action(HomeScreenAction.OnLongPressed(note.id)) },
                            onActionPerform = action
                        )
                    }
                }
            }
        }
    }

    EditNoteComponent(
        state = editNoteState,
        action = {}
    )
}