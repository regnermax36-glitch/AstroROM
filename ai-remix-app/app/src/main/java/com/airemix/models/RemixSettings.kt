package com.airemix.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemixSettings(
    val style: RemixStyle = RemixStyle.ELECTRONIC,
    val intensity: Float = 0.5f, // 0.0 to 1.0
    val tempo: TempoChange = TempoChange.KEEP_ORIGINAL,
    val effects: List<AudioEffect> = emptyList(),
    val outputQuality: OutputQuality = OutputQuality.HIGH,
    val fadeInDuration: Int = 2000, // milliseconds
    val fadeOutDuration: Int = 2000, // milliseconds
    val preserveVocals: Boolean = true,
    val addReverb: Boolean = false,
    val addDelay: Boolean = false,
    val bassBoost: Float = 0.0f, // -1.0 to 1.0
    val trebleBoost: Float = 0.0f // -1.0 to 1.0
) : Parcelable

@Parcelize
enum class RemixStyle : Parcelable {
    ELECTRONIC,
    HIP_HOP,
    AMBIENT,
    DANCE,
    DUBSTEP,
    HOUSE,
    TECHNO,
    CHILL,
    EXPERIMENTAL
}

@Parcelize
enum class TempoChange : Parcelable {
    SLOWER,
    KEEP_ORIGINAL,
    FASTER,
    DOUBLE_TIME,
    HALF_TIME
}

@Parcelize
enum class AudioEffect : Parcelable {
    REVERB,
    DELAY,
    CHORUS,
    FLANGER,
    DISTORTION,
    FILTER_SWEEP,
    PITCH_SHIFT,
    TIME_STRETCH,
    GRANULAR,
    VOCODER
}

@Parcelize
enum class OutputQuality : Parcelable {
    LOW(128),
    MEDIUM(192),
    HIGH(256),
    ULTRA(320);
    
    constructor(bitrate: Int) {
        this.bitrate = bitrate
    }
    
    val bitrate: Int
}

