package com.manyacov.feature_api_tracks.presentation.mapper

import com.manyacov.domain.avito_player.model.PlaylistTrack
import com.manyacov.ui_kit.list_items.TrackItem

fun PlaylistTrack.toTrackItem() = TrackItem(
    id = this.id.toString(),
    title = this.title,
    artistName = this.artistName,
    imageUrl = this.cover
)