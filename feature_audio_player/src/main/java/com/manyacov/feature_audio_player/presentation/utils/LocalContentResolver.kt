package com.manyacov.feature_audio_player.presentation.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.manyacov.feature_audio_player.presentation.model.Audio

fun getAudioFromPath(contentResolver: ContentResolver, filePath: String): Audio? {
    val uri = Uri.parse(filePath)

    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM_ID,
    )

    val cursor = contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        "${MediaStore.Audio.Media.DATA} = ?",
        arrayOf(filePath),
        null
    )

    cursor?.use {
        if (it.moveToFirst()) {
            val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
            val displayName = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
            val artist = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
            val title = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
            val albumId = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            val imageUrl = "content://media/external/audio/albumart/${it.getLong(albumId)}"

            return Audio(uri = uri, displayName = displayName, id = id, artist = artist, title = title, imageUrl = imageUrl)
        }
    }

    return null
}