package com.manyacov.data.avito_player.mapper

import android.net.Uri
import com.manyacov.data.avito_player.datasource.remote.model.TrackDto
import com.manyacov.domain.avito_player.model.AudioDomain
import com.manyacov.domain.avito_player.model.PlaylistTrack

internal fun TrackDto.toPlaylistTrack() = PlaylistTrack(
    id = this.id,
    title = this.title_short,
    artistName = this.artist.name,
    albumTitle = this.album.title,
    cover = this.album.cover,
)

fun TrackDto.mapToDomainAudio(): AudioDomain {
    return AudioDomain(
        uri = Uri.parse(this.preview),
        displayName = this.title_short,
        id = this.id,
        artist = this.artist.name,
        duration = this.duration,
        title = this.title,
        imageUrl = this.album.cover_big
    )
}