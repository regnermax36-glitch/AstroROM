package com.airemix.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemixResult(
    val id: String,
    val originalFile: AudioFile,
    val remixedFile: AudioFile,
    val settings: RemixSettings,
    val processingTime: Long, // milliseconds
    val createdAt: Long = System.currentTimeMillis(),
    val rating: Float? = null, // User rating 1-5 stars
    val isFavorite: Boolean = false,
    val tags: List<String> = emptyList(),
    val notes: String = ""
) : Parcelable {
    
    val processingTimeFormatted: String
        get() {
            val minutes = processingTime / 60000
            val seconds = (processingTime % 60000) / 1000
            return when {
                minutes > 0 -> "${minutes}m ${seconds}s"
                else -> "${seconds}s"
            }
        }
    
    val createdAtFormatted: String
        get() {
            val now = System.currentTimeMillis()
            val diff = now - createdAt
            
            return when {
                diff < 60000 -> "Just now"
                diff < 3600000 -> "${diff / 60000}m ago"
                diff < 86400000 -> "${diff / 3600000}h ago"
                else -> "${diff / 86400000}d ago"
            }
        }
    
    val hasRating: Boolean
        get() = rating != null && rating > 0
}

