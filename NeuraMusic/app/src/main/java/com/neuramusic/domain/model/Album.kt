package com.neuramusic.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val id: Long,
    val name: String,
    val artist: String,
    val artistId: Long,
    val songCount: Int,
    val year: Int,
    val duration: Long,
    val albumArt: String? = null,
    val genre: String? = null,
    val dateAdded: Long,
    val isCompilation: Boolean = false,
    val producer: String? = null,
    val label: String? = null
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

