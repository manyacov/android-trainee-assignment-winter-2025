package com.manyacov.feature_api_tracks.presentation.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    return when (itemCount) {
        0 -> remember(this) { LazyListState(0, 0) }
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}

val CombinedLoadStates.isLoading: Boolean
    get() = refresh is LoadState.Loading || append is LoadState.Loading
            || source.refresh is LoadState.Loading || source.append is LoadState.Loading

