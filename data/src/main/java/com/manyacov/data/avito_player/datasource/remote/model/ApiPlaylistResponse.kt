package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiPlaylistResponse(
    val tracks: TracksInfoDto,
)
