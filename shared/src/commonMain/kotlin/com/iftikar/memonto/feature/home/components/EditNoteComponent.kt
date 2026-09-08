package com.iftikar.memonto.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iftikar.memonto.feature.home.impl.EditNoteAction
import com.iftikar.memonto.feature.home.impl.EditNoteState

@Composable
fun EditNoteComponent(
    state: EditNoteState,
    action: (EditNoteAction) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(6.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            TextField(
                value = state.title,
                onValueChange = { action(EditNoteAction.OnTitleChange(it)) },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
//                placeholder = {
//                    if (state.title.isEmpty()) {
//                        Text(
//                            text = "Title cannot be empty",
//                            color = MaterialTheme.colorScheme.onSurface,
//                            fontSize = 32.sp,
//                            fontWeight = FontWeight.ExtraBold
//                        )
//                    }
//                }
            )
            TextField(
                value = "",
                onValueChange = {},
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

        }
    }
}