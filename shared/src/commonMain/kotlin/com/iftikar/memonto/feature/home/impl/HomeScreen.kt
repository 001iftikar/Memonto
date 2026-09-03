package com.iftikar.memonto.feature.home.impl

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iftikar.memonto.core.designsystem.component.card.NoteCard

@Composable
fun HomeScreen(
    listState: LazyListState,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            NoteCard(
                title = "Q3 OKR Strategy & Execution Plan",
                description = "Finalizing the objectives for the upcoming quarter focusing on expansion into the EMEA market. Key metrics include increasing active daily users by" +
                        " 15%ajshdashdkadskhasdhadhadhasdhkasjdhajdhkajdhahdajdhajdhajkdahhsdahdajdhahdhdsajhdajkdhaasdasdasdsaasdasdasddadasdadasdasdasdadadadadaddadasdadadadd" +
                        "adasdadadadad" +
                        "asdadsaddsadsaddsdasdskdjadjakda" +
                        "asdhsdhashdgsadgahdgasjdgsahjda" +
                        "adsashdgahsdashdghdjsdgasjhdadjds" +
                        "asdasdadsa" +
                        "asdadadsdasd" +
                        "asdasdasdadas" +
                        "asdasdasdsadsadasdadadadasdasdasdasd" +
                        "adsadadadsdasdsadasdadasd" +
                        "sadadsdasdadsadasd" +
                        "sdasdasdasdasdasdasdasdasdasdasdasdasddddddddddddddddddddddddddddasddddddddddddddddddddddddddsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "asddddddddddddddddddddddddddddddddd" +
                        "asddddddddddddddddddddddddddddd" +
                        "aSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
                        "ASSSSSSSSSSSSSSSSSSSSSSSSSSS" +
                        "ASSSSSSSSSS" +
                        "k...",
                timeText = "Updated 2 hours ago",
                isPinned = true
            )
        }

        // Note Item 2 (From your HTML/Image)
        items(20) {
            NoteCard(
                title = "Component Library Audit",
                description = "Reviewing the current state of our shared components JSON. Need to ensure all style_ keys are properly mapped to Tailwind utility classes. The focus is on...",
                timeText = "Yesterday",
                isPinned = false
            )
        }
    }
}