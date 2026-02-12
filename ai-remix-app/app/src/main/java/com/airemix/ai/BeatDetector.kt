package com.airemix.ai

import com.airemix.audio.AudioData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.*

class BeatDetector {
    
    companion object {
        private const val MIN_BPM = 60f
        private const val MAX_BPM = 200f
        private const val WINDOW_SIZE_MS = 100 // 100ms analysis windows
        private const val HOP_SIZE_MS = 25 // 25ms hop between windows
    }
    
    suspend fun detectBeats(audioData: AudioData): List<Float> = withContext(Dispatchers.Default) {
        val sampleRate = audioData.sampleRate
        val samples = audioData.samples
        
        // Calculate energy in overlapping windows
        val energyValues = calculateEnergyValues(samples, sampleRate)
        
        // Apply onset detection
        val onsetStrength = calculateOnsetStrength(energyValues)
        
        // Peak picking to find beat locations
        val beatTimes = pickBeats(onsetStrength, sampleRate)
        
        beatTimes
    }
    
    private fun calculateEnergyValues(samples: FloatArray, sampleRate: Int): List<Float> {
        val windowSizeSamples = (WINDOW_SIZE_MS * sampleRate / 1000)
        val hopSizeSamples = (HOP_SIZE_MS * sampleRate / 1000)
        val energyValues = mutableListOf<Float>()
        
        var windowStart = 0
        while (windowStart + windowSizeSamples < samples.size) {
            var energy = 0f
            
            // Calculate RMS energy for this window
            for (i in windowStart until windowStart + windowSizeSamples) {
                energy += samples[i] * samples[i]
            }
            
            energy = sqrt(energy / windowSizeSamples)
            energyValues.add(energy)
            
            windowStart += hopSizeSamples
        }
        
        return energyValues
    }
    
    private fun calculateOnsetStrength(energyValues: List<Float>): List<Float> {
        val onsetStrength = mutableListOf<Float>()
        
        // Use spectral flux-like approach on energy values
        for (i in 1 until energyValues.size) {
            val diff = energyValues[i] - energyValues[i - 1]
            // Only consider positive changes (increases in energy)
            onsetStrength.add(maxOf(0f, diff))
        }
        
        // Apply median filtering to reduce noise
        return medianFilter(onsetStrength, 3)
    }
    
    private fun medianFilter(values: List<Float>, windowSize: Int): List<Float> {
        val filtered = mutableListOf<Float>()
        val halfWindow = windowSize / 2
        
        for (i in values.indices) {
            val window = mutableListOf<Float>()
            
            for (j in maxOf(0, i - halfWindow)..minOf(values.size - 1, i + halfWindow)) {
                window.add(values[j])
            }
            
            window.sort()
            filtered.add(window[window.size / 2])
        }
        
        return filtered
    }
    
    private fun pickBeats(onsetStrength: List<Float>, sampleRate: Int): List<Float> {
        val beats = mutableListOf<Float>()
        
        if (onsetStrength.isEmpty()) return beats
        
        // Calculate adaptive threshold
        val meanOnset = onsetStrength.average().toFloat()
        val threshold = meanOnset * 0.3f // Adjust this factor as needed
        
        // Find local maxima above threshold
        val windowSize = 5 // Local maximum window
        val halfWindow = windowSize / 2
        
        for (i in halfWindow until onsetStrength.size - halfWindow) {
            val currentValue = onsetStrength[i]
            
            if (currentValue > threshold) {
                var isLocalMax = true
                
                // Check if it's a local maximum
                for (j in i - halfWindow until i + halfWindow) {
                    if (j != i && onsetStrength[j] >= currentValue) {
                        isLocalMax = false
                        break
                    }
                }
                
                if (isLocalMax) {
                    // Convert frame index to time in seconds
                    val timeInSeconds = i * HOP_SIZE_MS / 1000f
                    beats.add(timeInSeconds)
                }
            }
        }
        
        // Post-process beats to ensure minimum spacing
        return filterCloseBeats(beats, 0.1f) // Minimum 100ms between beats
    }
    
    private fun filterCloseBeats(beats: List<Float>, minSpacing: Float): List<Float> {
        if (beats.isEmpty()) return beats
        
        val filtered = mutableListOf<Float>()
        filtered.add(beats[0])
        
        for (i in 1 until beats.size) {
            if (beats[i] - filtered.last() >= minSpacing) {
                filtered.add(beats[i])
            }
        }
        
        return filtered
    }
    
    fun estimateTempo(beats: List<Float>): Float {
        if (beats.size < 2) return 120f // Default tempo
        
        // Calculate intervals between consecutive beats
        val intervals = mutableListOf<Float>()
        for (i in 1 until beats.size) {
            intervals.add(beats[i] - beats[i - 1])
        }
        
        if (intervals.isEmpty()) return 120f
        
        // Find the most common interval (mode)
        val intervalCounts = mutableMapOf<Int, Int>()
        
        for (interval in intervals) {
            // Round to nearest 10ms for grouping
            val roundedInterval = (interval * 100).roundToInt()
            intervalCounts[roundedInterval] = intervalCounts.getOrDefault(roundedInterval, 0) + 1
        }
        
        val mostCommonInterval = intervalCounts.maxByOrNull { it.value }?.key?.toFloat() ?: 50f
        val intervalInSeconds = mostCommonInterval / 100f
        
        // Convert to BPM
        val bpm = if (intervalInSeconds > 0) 60f / intervalInSeconds else 120f
        
        // Clamp to reasonable range
        return bpm.coerceIn(MIN_BPM, MAX_BPM)
    }
    
    fun analyzeRhythmicPattern(beats: List<Float>): RhythmicPattern {
        if (beats.size < 4) {
            return RhythmicPattern(
                isRegular = false,
                confidence = 0f,
                dominantPattern = "Unknown",
                complexity = 0f
            )
        }
        
        // Calculate intervals
        val intervals = mutableListOf<Float>()
        for (i in 1 until beats.size) {
            intervals.add(beats[i] - beats[i - 1])
        }
        
        // Analyze regularity
        val meanInterval = intervals.average().toFloat()
        val variance = intervals.map { (it - meanInterval) * (it - meanInterval) }.average().toFloat()
        val standardDeviation = sqrt(variance)
        
        val regularity = 1f - (standardDeviation / meanInterval).coerceIn(0f, 1f)
        val isRegular = regularity > 0.7f
        
        // Determine dominant pattern
        val dominantPattern = when {
            regularity > 0.9f -> "Steady"
            regularity > 0.7f -> "Regular"
            regularity > 0.5f -> "Moderate"
            else -> "Irregular"
        }
        
        // Calculate complexity based on interval variation
        val uniqueIntervals = intervals.map { (it * 10).roundToInt() }.toSet().size
        val complexity = (uniqueIntervals.toFloat() / intervals.size).coerceIn(0f, 1f)
        
        return RhythmicPattern(
            isRegular = isRegular,
            confidence = regularity,
            dominantPattern = dominantPattern,
            complexity = complexity
        )
    }
    
    fun quantizeBeats(beats: List<Float>, targetBpm: Float): List<Float> {
        if (beats.isEmpty()) return beats
        
        val beatInterval = 60f / targetBpm
        val quantized = mutableListOf<Float>()
        
        for (beat in beats) {
            // Find the nearest quantized beat position
            val nearestBeat = (beat / beatInterval).roundToInt() * beatInterval
            quantized.add(nearestBeat)
        }
        
        return quantized.distinct().sorted()
    }
}

data class RhythmicPattern(
    val isRegular: Boolean,
    val confidence: Float, // 0.0 to 1.0
    val dominantPattern: String,
    val complexity: Float // 0.0 to 1.0
) {
    val description: String
        get() = when {
            isRegular && complexity < 0.3f -> "Simple, steady rhythm"
            isRegular && complexity < 0.6f -> "Regular rhythm with some variation"
            isRegular -> "Complex but regular rhythm"
            complexity < 0.3f -> "Simple irregular rhythm"
            complexity < 0.6f -> "Moderately complex rhythm"
            else -> "Highly complex rhythm"
        }
}

