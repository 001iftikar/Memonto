package com.iftikar.memonto

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.iftikar.memonto.core.designsystem.component.bar.MemontoTopAppBar
import com.iftikar.memonto.core.designsystem.theme.MemontoTheme
import com.iftikar.memonto.feature.add_note.impl.navigation.addNoteEntryProvider
import com.iftikar.memonto.feature.bottom_navigation.Add
import com.iftikar.memonto.feature.bottom_navigation.BottomNav
import com.iftikar.memonto.feature.bottom_navigation.BottomNavigationAction
import com.iftikar.memonto.feature.bottom_navigation.Home
import com.iftikar.memonto.feature.bottom_navigation.MemontoBottomNavBar
import com.iftikar.memonto.feature.bottom_navigation.Settings
import com.iftikar.memonto.feature.home.impl.navigation.homeEntryProvider
import com.iftikar.memonto.feature.settings.impl.navigation.settingsEntryProvider
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun App() {
    MemontoTheme(
        darkTheme = true
    ) {
        Navigation()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSerializationApi::class)
@Composable
fun Navigation() {
    val config = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclassesOfSealed<BottomNav>()
//                subclass(HomeNavKey::class, HomeNavKey.serializer())
            }
        }
    }
    val backstack = rememberNavBackStack(config, Home)
    val listState = rememberLazyListState()
    val hazeState = retain { HazeState() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState),
            topBar = { MemontoTopAppBar() }
        ) { innerPadding ->
            NavDisplay(
                modifier = Modifier.padding(innerPadding),
                backStack = backstack,
                onBack = { backstack.removeLastOrNull() },
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                entryProvider = entryProvider {
                    homeEntryProvider(listState = listState, backStack = backstack)
                    settingsEntryProvider()
                    addNoteEntryProvider(backstack)
                },
                transitionSpec = {
                    if (backstack.lastOrNull() == Add) {
                        slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(300)
                        ) togetherWith slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(300)
                        )
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(500)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(500)
                        )
                    }
                },
                popTransitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(500)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(500)
                    )
                },
                predictivePopTransitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(500)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(500)
                    )
                }
            )
        }
        if (backstack.lastOrNull() != Add) {
            MemontoBottomNavBar(
                listState = listState,
                hazeState = hazeState,
                onNavigation = { action ->
                    val currentRoute = backstack.lastOrNull()
                    when (action) {
                        BottomNavigationAction.OnHomeClick -> {
                            if (currentRoute !is Home) {
                                backstack.clear()
                                backstack.add(Home)
                            }
                        }

                        BottomNavigationAction.OnAddClick -> {
                            if (currentRoute !is Add) {
                                backstack.add(Add)
                            }
                        }

                        BottomNavigationAction.OnSettingsClick -> {
                            if (currentRoute !is Settings) {
                                backstack.add(Settings)
                            }
                        }
                    }
                }
            )
        }
    }
}