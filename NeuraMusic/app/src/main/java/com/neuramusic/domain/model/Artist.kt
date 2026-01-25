package com.neuramusic.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    val id: Long,
    val name: String,
    val albumCount: Int,
    val songCount: Int,
    val duration: Long,
    val artistArt: String? = null,
    val biography: String? = null,
    val genre: String? = null,
    val country: String? = null,
    val formedYear: Int? = null,
    val isFollowing: Boolean = false
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

