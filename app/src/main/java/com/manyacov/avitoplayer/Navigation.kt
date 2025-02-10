package com.manyacov.avitoplayer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manyacov.avitoplayer.navigation.BottomNavigationBar
import com.manyacov.avitoplayer.navigation.NavItem
import com.manyacov.common.Constants.BOTTOM_NAV_CHANGING_DURATION
import com.manyacov.common.Constants.SCREEN_CHANGING_DURATION
import com.manyacov.avitoplayer.di.daggerViewModel
import com.manyacov.feature_downloaded_tracks.di.DaggerDownloadedScreenComponent
import com.manyacov.feature_downloaded_tracks.presentation.DownloadedScreen
import com.manyacov.feature_downloaded_tracks.presentation.DownloadedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Navigation() {
    val navController = rememberNavController()

    var bottomBarState by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collectLatest { backStackEntry ->
            delay(BOTTOM_NAV_CHANGING_DURATION)
            bottomBarState = backStackEntry.destination.route != NavItem.Song.path
        }
    }

    Scaffold(
        bottomBar = {
            if (bottomBarState) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        BackHandler(enabled = navController.currentBackStackEntry != null) {
            navController.navigateUp()
        }

        NavHost(navController, startDestination = NavItem.Downloaded.path) {
            composable(
                route = NavItem.Downloaded.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) }
            ) {
                val component = DaggerDownloadedScreenComponent.builder().build()

                val viewModel: DownloadedViewModel = daggerViewModel { component.getViewModel() }

                DownloadedScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel
                )
            }
            composable(
                route = NavItem.Online.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) })
            {

            }
            composable(
                route = NavItem.Song.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) })
            {

            }
        }
    }
}