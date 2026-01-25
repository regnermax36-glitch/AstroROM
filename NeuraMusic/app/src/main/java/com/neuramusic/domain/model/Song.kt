package com.neuramusic.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val path: String,
    val albumId: Long,
    val artistId: Long,
    val size: Long,
    val dateAdded: Long,
    val dateModified: Long,
    val year: Int,
    val track: Int,
    val genre: String? = null,
    val bitrate: Int? = null,
    val sampleRate: Int? = null,
    val albumArt: String? = null,
    val isFavorite: Boolean = false,
    val playCount: Int = 0,
    val lastPlayed: Long = 0L,
    val bpm: Int? = null,
    val key: String? = null,
    val mood: String? = null,
    val energy: Float? = null,
    val danceability: Float? = null,
    val valence: Float? = null
) : Parcelable {
    
    val displayDuration: String
        get() {
            val minutes = duration / 1000 / 60
            val seconds = (duration / 1000) % 60
            return String.format("%d:%02d", minutes, seconds)
        }
    
    val displaySize: String
        get() {
            val sizeInMB = size / (1024 * 1024)
            return if (sizeInMB > 0) "${sizeInMB}MB" else "${size / 1024}KB"
        }
}

