package com.manyacov.data.avito_player.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TracksInfoDto(
    val data: List<TrackDto>,
    val total: Int
)