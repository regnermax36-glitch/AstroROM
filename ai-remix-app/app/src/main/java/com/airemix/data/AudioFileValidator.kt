package com.airemix.data

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.airemix.models.AudioFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioFileValidator(private val context: Context) {
    
    companion object {
        private const val MIN_DURATION_MS = 10000L // 10 seconds
        private const val MAX_DURATION_MS = 600000L // 10 minutes
        private const val MIN_FILE_SIZE = 100 * 1024L // 100 KB
        private const val MAX_FILE_SIZE = 50 * 1024 * 1024L // 50 MB
        
        private val SUPPORTED_MIME_TYPES = setOf(
            "audio/mpeg",
            "audio/mp3",
            "audio/x-mp3",
            "audio/mpeg3",
            "audio/x-mpeg-3"
        )
        
        private val SUPPORTED_EXTENSIONS = setOf("mp3", "mpeg")
    }
    
    suspend fun validateAudioFile(audioFile: AudioFile): ValidationResult = withContext(Dispatchers.IO) {
        val errors = mutableListOf<ValidationError>()
        
        // Check file size
        if (audioFile.size < MIN_FILE_SIZE) {
            errors.add(ValidationError.FILE_TOO_SMALL)
        } else if (audioFile.size > MAX_FILE_SIZE) {
            errors.add(ValidationError.FILE_TOO_LARGE)
        }
        
        // Check MIME type
        if (!SUPPORTED_MIME_TYPES.any { audioFile.mimeType.contains(it, ignoreCase = true) }) {
            errors.add(ValidationError.UNSUPPORTED_FORMAT)
        }
        
        // Check file extension
        val extension = audioFile.displayName.substringAfterLast('.', "").lowercase()
        if (!SUPPORTED_EXTENSIONS.contains(extension)) {
            errors.add(ValidationError.UNSUPPORTED_EXTENSION)
        }
        
        // Validate audio metadata using MediaMetadataRetriever
        try {
            val metadata = extractAudioMetadata(audioFile.uri)
            
            if (metadata.duration < MIN_DURATION_MS) {
                errors.add(ValidationError.DURATION_TOO_SHORT)
            } else if (metadata.duration > MAX_DURATION_MS) {
                errors.add(ValidationError.DURATION_TOO_LONG)
            }
            
            if (metadata.bitrate != null && metadata.bitrate < 64) {
                errors.add(ValidationError.BITRATE_TOO_LOW)
            }
            
            if (metadata.sampleRate != null && metadata.sampleRate < 22050) {
                errors.add(ValidationError.SAMPLE_RATE_TOO_LOW)
            }
            
        } catch (e: Exception) {
            errors.add(ValidationError.CORRUPTED_FILE)
        }
        
        ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors,
            warnings = generateWarnings(audioFile)
        )
    }
    
    private suspend fun extractAudioMetadata(uri: Uri): AudioMetadata = withContext(Dispatchers.IO) {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, uri)
            
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: 0L
            val bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)?.toIntOrNull()
            val sampleRate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_SAMPLERATE)?.toIntOrNull()
            val channels = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS)?.toIntOrNull()
            
            AudioMetadata(
                duration = duration,
                bitrate = bitrate,
                sampleRate = sampleRate,
                channels = channels
            )
        } finally {
            try {
                retriever.release()
            } catch (e: Exception) {
                // Ignore release errors
            }
        }
    }
    
    private fun generateWarnings(audioFile: AudioFile): List<ValidationWarning> {
        val warnings = mutableListOf<ValidationWarning>()
        
        if (audioFile.size > 20 * 1024 * 1024) { // 20 MB
            warnings.add(ValidationWarning.LARGE_FILE_SIZE)
        }
        
        if (audioFile.duration > 300000) { // 5 minutes
            warnings.add(ValidationWarning.LONG_DURATION)
        }
        
        return warnings
    }
    
    fun getSupportedFormats(): List<String> = SUPPORTED_EXTENSIONS.toList()
    
    fun getMaxFileSize(): Long = MAX_FILE_SIZE
    
    fun getMaxDuration(): Long = MAX_DURATION_MS
    
    private data class AudioMetadata(
        val duration: Long,
        val bitrate: Int?,
        val sampleRate: Int?,
        val channels: Int?
    )
}

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<ValidationError>,
    val warnings: List<ValidationWarning>
) {
    val hasErrors: Boolean get() = errors.isNotEmpty()
    val hasWarnings: Boolean get() = warnings.isNotEmpty()
    
    fun getErrorMessage(): String {
        return errors.joinToString("\n") { it.message }
    }
    
    fun getWarningMessage(): String {
        return warnings.joinToString("\n") { it.message }
    }
}

enum class ValidationError(val message: String) {
    FILE_TOO_SMALL("File is too small (minimum 100 KB)"),
    FILE_TOO_LARGE("File is too large (maximum 50 MB)"),
    DURATION_TOO_SHORT("Audio is too short (minimum 10 seconds)"),
    DURATION_TOO_LONG("Audio is too long (maximum 10 minutes)"),
    UNSUPPORTED_FORMAT("Unsupported audio format"),
    UNSUPPORTED_EXTENSION("Unsupported file extension"),
    BITRATE_TOO_LOW("Audio bitrate is too low (minimum 64 kbps)"),
    SAMPLE_RATE_TOO_LOW("Sample rate is too low (minimum 22.05 kHz)"),
    CORRUPTED_FILE("File appears to be corrupted or unreadable")
}

enum class ValidationWarning(val message: String) {
    LARGE_FILE_SIZE("Large file size may result in longer processing time"),
    LONG_DURATION("Long audio duration may result in longer processing time"),
    LOW_QUALITY("Audio quality is lower than recommended for best results")
}

