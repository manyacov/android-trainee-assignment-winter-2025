package com.manyacov.data.avito_player.mapper

import com.manyacov.data.avito_player.datasource.remote.model.TrackDto
import com.manyacov.domain.avito_player.model.PlaylistTrack

internal fun TrackDto.toPlaylistTrack() = PlaylistTrack(
    id = this.id,
    title = this.title,
    artistName = this.artist.name,
    albumTitle = this.album.title,
    preview = this.preview,
)