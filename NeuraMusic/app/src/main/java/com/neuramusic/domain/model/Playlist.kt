package com.neuramusic.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlist(
    val id: Long,
    val name: String,
    val description: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
    val songCount: Int = 0,
    val duration: Long = 0L,
    val coverArt: String? = null,
    val isSmartPlaylist: Boolean = false,
    val smartCriteria: String? = null,
    val color: String? = null,
    val isPrivate: Boolean = false
) : Parcelable {
    
    val displayDuration: String
        get() {
            val hours = duration / 1000 / 3600
            val minutes = (duration / 1000 / 60) % 60
            return if (hours > 0) {
                String.format("%dh %dm", hours, minutes)
            } else {
                String.format("%dm", minutes)
            }
        }
}

