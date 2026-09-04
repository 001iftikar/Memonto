package com.iftikar.memonto.core.designsystem.component.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iftikar.memonto.core.model.Note
import com.iftikar.memonto.feature.home.impl.HomeScreenAction

@Composable
fun NoteCard(
    note: Note,
    timeStampText: String,
    longPressedVisible: Boolean,
    onLongPressed: () -> Unit,
    onActionPerform: (HomeScreenAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .heightIn(min = 170.dp, max = 400.dp)
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .combinedClickable(
                    onClick = { /* Normal click */ },
                    onLongClick = onLongPressed
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (note.relationTo != null) {
                        Box(
                            modifier = Modifier
                                .weight(0.5f, fill = false)
                                .background(
                                    color = MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.3f
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 6.dp)
                        ) {
                            Text(
                                text = note.relationTo,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }

                    if (note.pinnedAt != null) {
                        Spacer(Modifier.width(24.dp))
                        Icon(
                            imageVector = Icons.Outlined.PushPin,
                            contentDescription = "Pinned",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = note.title,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = note.body,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    maxLines = 12,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (note.pinnedAt == null) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Time",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.7f
                            ),
                            modifier = Modifier
                                .size(14.dp)
                                .padding(end = 4.dp)
                        )
                    }

                    Text(
                        text = timeStampText,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = 0.7f
                        ),
                        fontSize = 12.sp
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = longPressedVisible,
            enter = expandHorizontally(
                expandFrom = Alignment.Start,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300)),
            exit = shrinkHorizontally(
                shrinkTowards = Alignment.Start,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        ) {
            LongPressedCard(
                pinnedAt = note.pinnedAt,
                onPinClick = if (note.pinnedAt == null) {
                     { onActionPerform(HomeScreenAction.OnPinPress(note.id))
                     onLongPressed()}
                } else {
                    { onActionPerform(HomeScreenAction.OnUnPinPress(note.id)); onLongPressed() }
                },
                onDeleteClick = { onActionPerform(HomeScreenAction.OnDeletePress(note.id)) },
                onEditClick = {}
            )
        }
    }
}

@Composable
private fun LongPressedCard(
    pinnedAt: Long?,
    onPinClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
    ) {
        Column(
            modifier = Modifier
                .height(180.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            UtilityChip(
                icon = Icons.Default.PushPin,
                text = if (pinnedAt == null) "Pin" else "Unpin",
                onClick = onPinClick
            )
            UtilityChip(
                icon = Icons.Outlined.EditNote,
                text = "Edit",
                onClick = onEditClick
            )
            UtilityChip(
                icon = Icons.Outlined.DeleteOutline,
                text = "Delete",
                onClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun UtilityChip(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text
        )
        Text(text = text)
    }
}













