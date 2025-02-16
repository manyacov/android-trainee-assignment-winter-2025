package com.manyacov.data.avito_player.mapper

import com.manyacov.data.avito_player.datasource.local.model.PlaylistInfoEntity

fun String.toPlaylistInfoEntity() = PlaylistInfoEntity(path = this)