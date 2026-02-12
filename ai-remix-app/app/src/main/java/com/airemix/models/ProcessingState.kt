package com.airemix.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProcessingState(
    val id: String,
    val status: ProcessingStatus,
    val progress: Float = 0f, // 0.0 to 1.0
    val currentStep: ProcessingStep = ProcessingStep.INITIALIZING,
    val message: String = "",
    val startTime: Long = System.currentTimeMillis(),
    val estimatedTimeRemaining: Long = 0, // milliseconds
    val error: String? = null
) : Parcelable {
    
    val isCompleted: Boolean
        get() = status == ProcessingStatus.COMPLETED
    
    val isFailed: Boolean
        get() = status == ProcessingStatus.FAILED
    
    val isProcessing: Boolean
        get() = status == ProcessingStatus.PROCESSING
    
    val elapsedTime: Long
        get() = System.currentTimeMillis() - startTime
    
    val progressPercentage: Int
        get() = (progress * 100).toInt()
}

@Parcelize
enum class ProcessingStatus : Parcelable {
    IDLE,
    INITIALIZING,
    PROCESSING,
    COMPLETED,
    FAILED,
    CANCELLED
}

@Parcelize
enum class ProcessingStep : Parcelable {
    INITIALIZING,
    LOADING_AUDIO,
    ANALYZING_AUDIO,
    EXTRACTING_FEATURES,
    APPLYING_AI_MODEL,
    GENERATING_REMIX,
    APPLYING_EFFECTS,
    ENCODING_OUTPUT,
    SAVING_FILE,
    COMPLETED;
    
    val displayName: String
        get() = when (this) {
            INITIALIZING -> "Initializing..."
            LOADING_AUDIO -> "Loading audio file..."
            ANALYZING_AUDIO -> "Analyzing audio structure..."
            EXTRACTING_FEATURES -> "Extracting musical features..."
            APPLYING_AI_MODEL -> "Applying AI remix model..."
            GENERATING_REMIX -> "Generating remix..."
            APPLYING_EFFECTS -> "Applying audio effects..."
            ENCODING_OUTPUT -> "Encoding output..."
            SAVING_FILE -> "Saving remix file..."
            COMPLETED -> "Remix completed!"
        }
    
    val weight: Float
        get() = when (this) {
            INITIALIZING -> 0.05f
            LOADING_AUDIO -> 0.10f
            ANALYZING_AUDIO -> 0.15f
            EXTRACTING_FEATURES -> 0.20f
            APPLYING_AI_MODEL -> 0.30f
            GENERATING_REMIX -> 0.25f
            APPLYING_EFFECTS -> 0.15f
            ENCODING_OUTPUT -> 0.10f
            SAVING_FILE -> 0.05f
            COMPLETED -> 1.0f
        }
}

