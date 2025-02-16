package com.manyacov.feature_downloaded_tracks.presentation.mapper

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import com.manyacov.ui_kit.list_items.TrackItem
import java.io.File

fun convertToTrackItem(filePath: String): TrackItem? {

    var retriever = MediaMetadataRetriever()

    return try {
        val file = File(filePath)

        if (file.exists()) {
            retriever = MediaMetadataRetriever()
            retriever.setDataSource(filePath)

            val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                ?: getAudioTitleFromPath(filePath)
            val artist =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                    ?: "Unknown Artist"

            val albumArtBitmap = retriever.embeddedPicture?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)
            }

            TrackItem(
                id = filePath,
                title = title,
                artistName = artist,
                albumArtBitmap = albumArtBitmap
            )
        } else {
            null
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

fun getAudioTitleFromPath(filePath: String): String {
    val fileName = filePath.substringAfterLast("/")
    return fileName.substringBeforeLast(".")
}