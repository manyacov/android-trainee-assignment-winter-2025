package com.manyacov.feature_audio_player.presentation

import com.manyacov.common.presentation.UiEffect
import com.manyacov.common.presentation.UiEvent
import com.manyacov.common.presentation.UiState

class AudioPlayerContract {
    sealed class Event : UiEvent {
        data object OnScreenOpened: Event()
        data object OnPlayPauseClicked: Event()
        data object OnNextClicked: Event()
        data object OnPreviousClicked: Event()
        data class OnChangeProgress(val newProgress: Float) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val isReady: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect
}