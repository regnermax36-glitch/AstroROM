package com.airemix.audio

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.*

class WaveformAnalyzer(private val context: Context) {
    
    companion object {
        private const val DEFAULT_SAMPLES_PER_PIXEL = 256
        private const val DEFAULT_HEIGHT = 200
        private const val DEFAULT_WIDTH = 800
    }
    
    suspend fun generateWaveform(
        uri: Uri,
        width: Int = DEFAULT_WIDTH,
        height: Int = DEFAULT_HEIGHT,
        samplesPerPixel: Int = DEFAULT_SAMPLES_PER_PIXEL
    ): WaveformData? = withContext(Dispatchers.Default) {
        
        val audioProcessor = AudioProcessor(context)
        val audioData = audioProcessor.extractAudioData(uri) ?: return@withContext null
        
        val samples = audioData.samples
        val totalSamples = samples.size
        val pixelCount = width
        val samplesPerPixelActual = maxOf(1, totalSamples / pixelCount)
        
        val peaks = FloatArray(pixelCount)
        val rms = FloatArray(pixelCount)
        
        for (pixel in 0 until pixelCount) {
            val startSample = pixel * samplesPerPixelActual
            val endSample = minOf(startSample + samplesPerPixelActual, totalSamples)
            
            var maxPeak = 0f
            var sumSquares = 0.0
            var sampleCount = 0
            
            for (i in startSample until endSample) {
                val sample = abs(samples[i])
                maxPeak = maxOf(maxPeak, sample)
                sumSquares += sample * sample
                sampleCount++
            }
            
            peaks[pixel] = maxPeak
            rms[pixel] = if (sampleCount > 0) sqrt(sumSquares / sampleCount).toFloat() else 0f
        }
        
        WaveformData(
            peaks = peaks,
            rms = rms,
            duration = audioData.duration,
            sampleRate = audioData.sampleRate,
            width = width,
            height = height
        )
    }
    
    suspend fun generateWaveformBitmap(
        waveformData: WaveformData,
        primaryColor: Int = 0xFF2196F3.toInt(),
        secondaryColor: Int = 0x802196F3.toInt(),
        backgroundColor: Int = 0xFF000000.toInt()
    ): Bitmap = withContext(Dispatchers.Default) {
        
        val bitmap = Bitmap.createBitmap(
            waveformData.width,
            waveformData.height,
            Bitmap.Config.ARGB_8888
        )
        
        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)
        
        val primaryPaint = Paint().apply {
            color = primaryColor
            isAntiAlias = true
            strokeWidth = 1f
            style = Paint.Style.FILL
        }
        
        val secondaryPaint = Paint().apply {
            color = secondaryColor
            isAntiAlias = true
            strokeWidth = 1f
            style = Paint.Style.FILL
        }
        
        val centerY = waveformData.height / 2f
        val maxAmplitude = waveformData.height / 2f * 0.9f // Leave some margin
        
        // Draw RMS (background waveform)
        val rmsPath = Path()
        val rmsPathBottom = Path()
        
        for (i in waveformData.rms.indices) {
            val x = i.toFloat()
            val amplitude = waveformData.rms[i] * maxAmplitude
            
            if (i == 0) {
                rmsPath.moveTo(x, centerY - amplitude)
                rmsPathBottom.moveTo(x, centerY + amplitude)
            } else {
                rmsPath.lineTo(x, centerY - amplitude)
                rmsPathBottom.lineTo(x, centerY + amplitude)
            }
        }
        
        canvas.drawPath(rmsPath, secondaryPaint)
        canvas.drawPath(rmsPathBottom, secondaryPaint)
        
        // Draw peaks (foreground waveform)
        val peakPath = Path()
        val peakPathBottom = Path()
        
        for (i in waveformData.peaks.indices) {
            val x = i.toFloat()
            val amplitude = waveformData.peaks[i] * maxAmplitude
            
            if (i == 0) {
                peakPath.moveTo(x, centerY - amplitude)
                peakPathBottom.moveTo(x, centerY + amplitude)
            } else {
                peakPath.lineTo(x, centerY - amplitude)
                peakPathBottom.lineTo(x, centerY + amplitude)
            }
        }
        
        canvas.drawPath(peakPath, primaryPaint)
        canvas.drawPath(peakPathBottom, primaryPaint)
        
        bitmap
    }
    
    suspend fun analyzeBeats(audioData: AudioData): BeatAnalysis = withContext(Dispatchers.Default) {
        val samples = audioData.samples
        val sampleRate = audioData.sampleRate
        
        // Simple beat detection using energy-based approach
        val windowSize = sampleRate / 10 // 100ms windows
        val hopSize = windowSize / 4
        val energyValues = mutableListOf<Float>()
        
        for (i in 0 until samples.size - windowSize step hopSize) {
            var energy = 0f
            for (j in i until i + windowSize) {
                energy += samples[j] * samples[j]
            }
            energyValues.add(energy / windowSize)
        }
        
        // Find peaks in energy
        val beats = mutableListOf<Float>()
        val threshold = energyValues.average().toFloat() * 1.5f
        
        for (i in 1 until energyValues.size - 1) {
            if (energyValues[i] > threshold &&
                energyValues[i] > energyValues[i - 1] &&
                energyValues[i] > energyValues[i + 1]) {
                
                val timeInSeconds = i * hopSize.toFloat() / sampleRate
                beats.add(timeInSeconds)
            }
        }
        
        // Calculate tempo
        val intervals = mutableListOf<Float>()
        for (i in 1 until beats.size) {
            intervals.add(beats[i] - beats[i - 1])
        }
        
        val averageInterval = if (intervals.isNotEmpty()) intervals.average().toFloat() else 0.5f
        val tempo = if (averageInterval > 0) 60f / averageInterval else 120f
        
        BeatAnalysis(
            beats = beats,
            tempo = tempo,
            confidence = calculateBeatConfidence(intervals)
        )
    }
    
    private fun calculateBeatConfidence(intervals: List<Float>): Float {
        if (intervals.isEmpty()) return 0f
        
        val mean = intervals.average().toFloat()
        val variance = intervals.map { (it - mean) * (it - mean) }.average().toFloat()
        val standardDeviation = sqrt(variance)
        
        // Lower standard deviation means more consistent beat intervals
        return maxOf(0f, 1f - (standardDeviation / mean))
    }
    
    fun getWaveformProgress(waveformData: WaveformData, currentPositionMs: Long): Float {
        val totalDurationMs = waveformData.duration / 1000 // Convert to milliseconds
        return if (totalDurationMs > 0) {
            (currentPositionMs.toFloat() / totalDurationMs).coerceIn(0f, 1f)
        } else 0f
    }
    
    fun getPositionFromWaveform(waveformData: WaveformData, progress: Float): Long {
        val totalDurationMs = waveformData.duration / 1000
        return (progress * totalDurationMs).toLong()
    }
}

data class WaveformData(
    val peaks: FloatArray,
    val rms: FloatArray,
    val duration: Long,
    val sampleRate: Int,
    val width: Int,
    val height: Int
) {
    val pixelCount: Int get() = peaks.size
    
    val maxPeak: Float get() = peaks.maxOrNull() ?: 0f
    
    val averageRms: Float get() = rms.average().toFloat()
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as WaveformData
        
        if (!peaks.contentEquals(other.peaks)) return false
        if (!rms.contentEquals(other.rms)) return false
        if (duration != other.duration) return false
        if (sampleRate != other.sampleRate) return false
        if (width != other.width) return false
        if (height != other.height) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = peaks.contentHashCode()
        result = 31 * result + rms.contentHashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + sampleRate
        result = 31 * result + width
        result = 31 * result + height
        return result
    }
}

data class BeatAnalysis(
    val beats: List<Float>, // Beat positions in seconds
    val tempo: Float, // BPM
    val confidence: Float // 0.0 to 1.0
) {
    val beatCount: Int get() = beats.size
    
    val isReliable: Boolean get() = confidence > 0.7f
    
    val tempoCategory: String
        get() = when {
            tempo < 60 -> "Very Slow"
            tempo < 90 -> "Slow"
            tempo < 120 -> "Moderate"
            tempo < 140 -> "Fast"
            tempo < 180 -> "Very Fast"
            else -> "Extremely Fast"
        }
}

