package com.airemix.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AudioFile(
    val id: String,
    val uri: Uri,
    val name: String,
    val displayName: String,
    val size: Long,
    val duration: Long, // in milliseconds
    val mimeType: String,
    val path: String? = null,
    val artist: String? = null,
    val album: String? = null,
    val bitrate: Int? = null,
    val sampleRate: Int? = null,
    val channels: Int? = null,
    val dateAdded: Long = System.currentTimeMillis()
) : Parcelable {
    
    val durationFormatted: String
        get() {
            val minutes = duration / 60000
            val seconds = (duration % 60000) / 1000
            return String.format("%d:%02d", minutes, seconds)
        }
    
    val sizeFormatted: String
        get() {
            return when {
                size < 1024 -> "$size B"
                size < 1024 * 1024 -> "${size / 1024} KB"
                else -> "${size / (1024 * 1024)} MB"
            }
        }
    
    val isValidMp3: Boolean
        get() = mimeType.contains("audio") && 
                (mimeType.contains("mpeg") || mimeType.contains("mp3")) &&
                duration > 0
}

