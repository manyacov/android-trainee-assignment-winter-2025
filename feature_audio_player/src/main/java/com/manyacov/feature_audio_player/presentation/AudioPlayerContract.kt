package com.manyacov.feature_audio_player.presentation

import com.manyacov.common.presentation.UiEffect
import com.manyacov.common.presentation.UiEvent
import com.manyacov.common.presentation.UiState
import com.manyacov.feature_audio_player.presentation.model.PlayerTime

class AudioPlayerContract {
    sealed class Event : UiEvent {
        data object OnScreenOpened: Event()
        data object OnPlayPauseClicked: Event()
        data class OnNextClicked(val currentTrackId: String): Event()
        data class OnPreviousClicked(val currentTrackId: String): Event()
        data class OnChangeProgress(val newProgress: Float) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val playerTime: PlayerTime = PlayerTime("00:00", "00:00"),
        val isReady: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect
}