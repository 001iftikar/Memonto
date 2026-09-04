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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.iftikar.memonto.core.designsystem.component.bar.MemontoTopAppBar
import com.iftikar.memonto.core.designsystem.theme.MemontoTheme
import com.iftikar.memonto.core.util.TimeOfDay
import com.iftikar.memonto.core.util.getTimeOfDay
import com.iftikar.memonto.feature.add_note.impl.navigation.addNoteEntryProvider
import com.iftikar.memonto.feature.bottom_navigation.Add
import com.iftikar.memonto.feature.bottom_navigation.BottomNav
import com.iftikar.memonto.feature.bottom_navigation.BottomNavigationAction
import com.iftikar.memonto.feature.bottom_navigation.Home
import com.iftikar.memonto.feature.bottom_navigation.MemontoBottomNavBar
import com.iftikar.memonto.feature.bottom_navigation.Settings
import com.iftikar.memonto.feature.global.GlobalViewModel
import com.iftikar.memonto.feature.home.impl.navigation.homeEntryProvider
import com.iftikar.memonto.feature.settings.impl.navigation.settingsEntryProvider
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MemontoTheme {
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
    val globalViewModel = koinViewModel<GlobalViewModel>()
    val userNameState by globalViewModel.showUsernameState.collectAsStateWithLifecycle()
    val currentTimeMillis by globalViewModel.currentTime.collectAsStateWithLifecycle()
    val greet = when(getTimeOfDay(currentTimeMillis)) {
        TimeOfDay.MORNING -> "Good Morning!"
        TimeOfDay.AFTERNOON -> "Good Afternoon!"
        TimeOfDay.EVENING -> "Good Evening!"
        TimeOfDay.NIGHT -> "Are we not sleeping today?"
    }

    val listState = rememberLazyListState()
    val hazeState = retain { HazeState() }
    val snackbarHostState = retain { SnackbarHostState() }

    // 2. Create a coroutine scope to launch the animation
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState),
            topBar = { MemontoTopAppBar(
                isLoading = userNameState.isLoading,
                user = userNameState.user,
                greet = greet
            ) },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 120.dp)
                ) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
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
                    homeEntryProvider(listState = listState, backStack = backstack, showError = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = it,
                                duration = SnackbarDuration.Long,
                                withDismissAction = true
                            )
                        }
                    })
                    settingsEntryProvider()
                    addNoteEntryProvider(backStack = backstack, showError = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = it,
                                duration = SnackbarDuration.Long,
                                withDismissAction = true
                            )
                        }
                    })
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
            val currentRoute = backstack.lastOrNull()
            MemontoBottomNavBar(
                listState = listState,
                hazeState = hazeState,
                currentRoute = (currentRoute?: Home) as BottomNav,
                onNavigation = { action ->
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