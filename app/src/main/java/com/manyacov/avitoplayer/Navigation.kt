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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manyacov.avitoplayer.navigation.BottomNavigationBar
import com.manyacov.avitoplayer.navigation.NavItem
import com.manyacov.common.Constants.BOTTOM_NAV_CHANGING_DURATION
import com.manyacov.common.Constants.SCREEN_CHANGING_DURATION
import com.manyacov.feature_api_tracks.presentation.ApiPlaylistScreen
import com.manyacov.feature_api_tracks.presentation.ApiPlaylistViewModel
import com.manyacov.feature_audio_player.presentation.local.LocalAudioPlayerScreen
import com.manyacov.feature_audio_player.presentation.local.LocalAudioPlayerViewModel
import com.manyacov.feature_audio_player.presentation.remote.RemoteAudioPlayerScreen
import com.manyacov.feature_audio_player.presentation.remote.RemoteAudioPlayerViewModel
import com.manyacov.feature_downloaded_tracks.presentation.DownloadedScreen
import com.manyacov.feature_downloaded_tracks.presentation.DownloadedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Navigation(
    startService: () -> Unit = {}
) {
    val navController = rememberNavController()

    var isBottomBarVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collectLatest { backStackEntry ->
            delay(BOTTOM_NAV_CHANGING_DURATION)
            isBottomBarVisible =
                backStackEntry.destination.route == NavItem.Online.path
                        || backStackEntry.destination.route == NavItem.Downloaded.path
        }
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
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
                val viewModel = hiltViewModel<DownloadedViewModel>()

                DownloadedScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                route = NavItem.Online.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) })
            {
                val viewModel = hiltViewModel<ApiPlaylistViewModel>()

                ApiPlaylistScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                route = NavItem.LocalPlayer.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) })
            {
                val viewModel = hiltViewModel<LocalAudioPlayerViewModel>()

                LocalAudioPlayerScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                )

                startService()
            }
            composable(
                route = NavItem.ApiPlayer.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) })
            {
                val viewModel = hiltViewModel<RemoteAudioPlayerViewModel>()

                RemoteAudioPlayerScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                )

                startService()
            }
        }
    }
}