package com.iftikar.memonto.feature.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun MemontoBottomNavBar(
    listState: LazyListState,
    hazeState: HazeState,
    onNavigation: (BottomNavigationAction) -> Unit,
    currentRoute: BottomNav,
    modifier: Modifier = Modifier
) {
    var isScrollingUp by retain { mutableStateOf(true) }
    LaunchedEffect(listState) {
        var previousIndex = listState.firstVisibleItemIndex
        var previousScrollOffset = listState.firstVisibleItemScrollOffset

        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.collect { (index, offset) ->
            if (index == 0 && offset == 0) {
                isScrollingUp = true
            } else if (index != previousIndex) {
                isScrollingUp = index < previousIndex
            } else {
                isScrollingUp = offset < previousScrollOffset
            }
            previousIndex = index
            previousScrollOffset = offset
        }
    }

    AnimatedVisibility(
        visible = isScrollingUp,
        modifier = modifier,
        enter = slideInVertically(animationSpec = tween(400), initialOffsetY = { it * 2 }),
        exit = slideOutVertically(animationSpec = tween(400), targetOffsetY = { it * 2 })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                // The Surface color itself must be transparent so the Haze tint shows through
                color = Color.Transparent,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .hazeEffect(
                            state = hazeState,
                            style = HazeStyle(
                                tint = HazeTint(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                blurRadius = 16.dp
                            )
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {onNavigation(BottomNavigationAction.OnHomeClick)},
                        modifier = Modifier.padding(start = 24.dp).size(48.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Description,
                            contentDescription = "Notes",
                            tint = if (currentRoute == Home) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Spacer(modifier = Modifier.size(64.dp))

                    IconButton(
                        onClick = {onNavigation(BottomNavigationAction.OnSettingsClick)},
                        modifier = Modifier.padding(end = 24.dp).size(48.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            tint = if (currentRoute == Settings) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            FloatingActionButton(
                onClick = {onNavigation(BottomNavigationAction.OnAddClick)},
                modifier = Modifier.align(Alignment.Center).size(64.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add note",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}