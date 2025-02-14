package com.manyacov.feature_downloaded_tracks.presentation.mapper

import android.media.MediaMetadataRetriever
import com.manyacov.ui_kit.list_items.TrackItem

fun convertToTrackItem(filePath: String): TrackItem {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(filePath)

    val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) ?: getAudioTitleFromPath(filePath)
    val artist =
        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) ?: "Unknown Artist"
    //val albumArtUri = retriever//.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_PRIMARY)

    val albumArtBitmap = retriever.embeddedPicture
    val albumArtUrl = if (albumArtBitmap != null) {
        saveBitmapToFile(albumArtBitmap)
    } else {
        null
    }

    retriever.release()

    return TrackItem(
        id = filePath,
        title = title,
        artistName = artist,
        imageUrl = albumArtUrl
    )
}

fun getAudioTitleFromPath(filePath: String): String {
    val fileName = filePath.substringAfterLast("/")
    return fileName.substringBeforeLast(".")
}

fun saveBitmapToFile(bitmap: ByteArray): String {
    return "path/to/saved/image"
}