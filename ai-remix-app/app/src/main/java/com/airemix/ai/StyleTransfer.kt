package com.airemix.ai

import android.content.Context
import com.airemix.models.RemixStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.*
import kotlin.random.Random

class StyleTransfer(private val context: Context) {
    
    companion object {
        private const val STYLE_INTENSITY_FACTOR = 0.8f
        private const val HARMONIC_ENHANCEMENT_FACTOR = 0.6f
        private const val RHYTHM_MODIFICATION_FACTOR = 0.7f
    }
    
    suspend fun applyStyleTransfer(
        audioFeatures: AudioFeatures,
        targetStyle: RemixStyle,
        intensity: Float
    ): AudioFeatures = withContext(Dispatchers.Default) {
        
        when (targetStyle) {
            RemixStyle.ELECTRONIC -> applyElectronicTransfer(audioFeatures, intensity)
            RemixStyle.HIP_HOP -> applyHipHopTransfer(audioFeatures, intensity)
            RemixStyle.AMBIENT -> applyAmbientTransfer(audioFeatures, intensity)
            RemixStyle.DANCE -> applyDanceTransfer(audioFeatures, intensity)
            RemixStyle.DUBSTEP -> applyDubstepTransfer(audioFeatures, intensity)
            RemixStyle.HOUSE -> applyHouseTransfer(audioFeatures, intensity)
            RemixStyle.TECHNO -> applyTechnoTransfer(audioFeatures, intensity)
            RemixStyle.CHILL -> applyChillTransfer(audioFeatures, intensity)
            RemixStyle.EXPERIMENTAL -> applyExperimentalTransfer(audioFeatures, intensity)
        }
    }
    
    private fun applyElectronicTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Enhance synthetic harmonics
        for (i in modifiedSpectrum.indices) {
            // Add digital artifacts and harmonic enhancement
            val harmonicBoost = sin(i * 0.1f) * intensity * HARMONIC_ENHANCEMENT_FACTOR
            modifiedSpectrum[i] *= (1f + harmonicBoost)
            
            // Add subtle digital distortion
            if (modifiedSpectrum[i] > 0.7f) {
                modifiedSpectrum[i] = tanh(modifiedSpectrum[i] * (1f + intensity * 0.5f)).toFloat()
            }
        }
        
        // Modify rhythm to be more quantized
        val quantizedBeats = quantizeRhythm(features.beats, intensity)
        
        // Enhance high-frequency content
        val enhancedSpectrum = enhanceFrequencyRange(modifiedSpectrum, 0.6f, 1.0f, intensity * 0.4f)
        
        return features.copy(
            spectrum = enhancedSpectrum,
            beats = quantizedBeats,
            harmonicContent = features.harmonicContent * (1f + intensity * 0.3f)
        )
    }
    
    private fun applyHipHopTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Heavy bass emphasis
        val bassEnhanced = enhanceFrequencyRange(modifiedSpectrum, 0.0f, 0.2f, intensity * 0.8f)
        
        // Add rhythmic emphasis on strong beats
        val emphasizedBeats = emphasizeStrongBeats(features.beats, intensity)
        
        // Reduce mid-range frequencies slightly to make room for vocals
        val midReduced = reduceFrequencyRange(bassEnhanced, 0.3f, 0.6f, intensity * 0.2f)
        
        // Add subtle saturation for warmth
        for (i in midReduced.indices) {
            if (midReduced[i] > 0.5f) {
                midReduced[i] = tanh(midReduced[i] * (1f + intensity * 0.3f)).toFloat()
            }
        }
        
        return features.copy(
            spectrum = midReduced,
            beats = emphasizedBeats,
            bassContent = features.bassContent * (1f + intensity * 0.6f)
        )
    }
    
    private fun applyAmbientTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Smooth the spectrum for ethereal quality
        val smoothed = applySpectralSmoothing(modifiedSpectrum, intensity)
        
        // Reduce harsh frequencies
        val softened = reduceFrequencyRange(smoothed, 0.7f, 0.9f, intensity * 0.4f)
        
        // Add reverb-like spectral spreading
        val reverbSpectrum = addSpectralReverb(softened, intensity)
        
        // Make rhythm less prominent
        val softenedBeats = softenRhythm(features.beats, intensity)
        
        // Enhance harmonic content for richness
        val harmonicEnhanced = reverbSpectrum.mapIndexed { i, value ->
            val harmonicMultiplier = 1f + sin(i * 0.05f) * intensity * 0.2f
            value * harmonicMultiplier
        }.toFloatArray()
        
        return features.copy(
            spectrum = harmonicEnhanced,
            beats = softenedBeats,
            harmonicContent = features.harmonicContent * (1f + intensity * 0.4f),
            ambientQuality = intensity
        )
    }
    
    private fun applyDanceTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Strong kick drum emphasis
        val kickEnhanced = enhanceFrequencyRange(modifiedSpectrum, 0.0f, 0.1f, intensity * 1.0f)
        
        // Enhance synth frequencies
        val synthEnhanced = enhanceFrequencyRange(kickEnhanced, 0.3f, 0.7f, intensity * 0.5f)
        
        // Make rhythm more driving
        val drivingBeats = makeBeatsDriving(features.beats, intensity)
        
        // Add energy to the overall spectrum
        val energized = synthEnhanced.map { it * (1f + intensity * 0.3f) }.toFloatArray()
        
        return features.copy(
            spectrum = energized,
            beats = drivingBeats,
            bassContent = features.bassContent * (1f + intensity * 0.7f),
            energy = features.energy * (1f + intensity * 0.5f)
        )
    }
    
    private fun applyDubstepTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Extreme bass boost
        val bassBoost = enhanceFrequencyRange(modifiedSpectrum, 0.0f, 0.15f, intensity * 1.2f)
        
        // Add wobble effect (frequency modulation)
        val wobbleSpectrum = addWobbleEffect(bassBoost, intensity)
        
        // Create dramatic drops and builds
        val dramaticBeats = createDramaticRhythm(features.beats, intensity)
        
        // Add aggressive high-frequency content
        val aggressiveHighs = enhanceFrequencyRange(wobbleSpectrum, 0.8f, 1.0f, intensity * 0.6f)
        
        return features.copy(
            spectrum = aggressiveHighs,
            beats = dramaticBeats,
            bassContent = features.bassContent * (1f + intensity * 1.0f),
            aggressiveness = intensity
        )
    }
    
    private fun applyHouseTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Four-on-the-floor kick pattern emphasis
        val kickPattern = createFourOnFloorPattern(features.beats, intensity)
        
        // Enhance mid-range for classic house sound
        val midEnhanced = enhanceFrequencyRange(modifiedSpectrum, 0.2f, 0.6f, intensity * 0.4f)
        
        // Add subtle filtering effects
        val filtered = applySubtleFiltering(midEnhanced, intensity)
        
        return features.copy(
            spectrum = filtered,
            beats = kickPattern,
            grooveQuality = intensity
        )
    }
    
    private fun applyTechnoTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Mechanical, repetitive rhythm
        val mechanicalBeats = createMechanicalRhythm(features.beats, intensity)
        
        // Industrial frequency shaping
        val industrial = applyIndustrialProcessing(modifiedSpectrum, intensity)
        
        // Enhance percussive elements
        val percussiveEnhanced = enhancePercussiveElements(industrial, intensity)
        
        return features.copy(
            spectrum = percussiveEnhanced,
            beats = mechanicalBeats,
            mechanicalQuality = intensity
        )
    }
    
    private fun applyChillTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Warm, relaxed processing
        val warmed = addWarmth(modifiedSpectrum, intensity)
        
        // Relaxed rhythm
        val relaxedBeats = relaxRhythm(features.beats, intensity)
        
        // Gentle high-frequency roll-off
        val gentle = applyGentleRolloff(warmed, intensity)
        
        return features.copy(
            spectrum = gentle,
            beats = relaxedBeats,
            warmth = intensity
        )
    }
    
    private fun applyExperimentalTransfer(features: AudioFeatures, intensity: Float): AudioFeatures {
        val modifiedSpectrum = features.spectrum.copyOf()
        
        // Random spectral modifications
        val experimental = applyRandomSpectralModifications(modifiedSpectrum, intensity)
        
        // Unconventional rhythm patterns
        val unconventionalBeats = createUnconventionalRhythm(features.beats, intensity)
        
        // Add glitch effects
        val glitched = addGlitchEffects(experimental, intensity)
        
        return features.copy(
            spectrum = glitched,
            beats = unconventionalBeats,
            experimentalFactor = intensity
        )
    }
    
    // Helper functions for spectral processing
    
    private fun enhanceFrequencyRange(spectrum: FloatArray, startRatio: Float, endRatio: Float, boost: Float): FloatArray {
        val result = spectrum.copyOf()
        val startIndex = (startRatio * spectrum.size).toInt()
        val endIndex = (endRatio * spectrum.size).toInt()
        
        for (i in startIndex until endIndex.coerceAtMost(spectrum.size)) {
            result[i] *= (1f + boost)
        }
        
        return result
    }
    
    private fun reduceFrequencyRange(spectrum: FloatArray, startRatio: Float, endRatio: Float, reduction: Float): FloatArray {
        val result = spectrum.copyOf()
        val startIndex = (startRatio * spectrum.size).toInt()
        val endIndex = (endRatio * spectrum.size).toInt()
        
        for (i in startIndex until endIndex.coerceAtMost(spectrum.size)) {
            result[i] *= (1f - reduction)
        }
        
        return result
    }
    
    private fun applySpectralSmoothing(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        val smoothingFactor = intensity * 0.3f
        
        for (i in 1 until result.size - 1) {
            val smoothed = result[i - 1] * smoothingFactor + 
                          result[i] * (1f - 2f * smoothingFactor) + 
                          result[i + 1] * smoothingFactor
            result[i] = smoothed
        }
        
        return result
    }
    
    private fun addSpectralReverb(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        val reverbAmount = intensity * 0.2f
        
        for (i in 10 until result.size) {
            result[i] += result[i - 10] * reverbAmount
        }
        
        return result
    }
    
    private fun addWobbleEffect(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        
        for (i in result.indices) {
            val wobble = sin(i * 0.1f) * intensity * 0.3f
            result[i] *= (1f + wobble)
        }
        
        return result
    }
    
    // Helper functions for rhythm processing
    
    private fun quantizeRhythm(beats: List<Float>, intensity: Float): List<Float> {
        if (beats.isEmpty()) return beats
        
        val quantized = mutableListOf<Float>()
        val gridSize = 0.25f // 16th note grid
        
        for (beat in beats) {
            val quantizedBeat = (beat / gridSize).roundToInt() * gridSize
            val finalBeat = beat + (quantizedBeat - beat) * intensity
            quantized.add(finalBeat)
        }
        
        return quantized
    }
    
    private fun emphasizeStrongBeats(beats: List<Float>, intensity: Float): List<Float> {
        // This would modify beat strengths, but since we're working with times,
        // we'll return the original beats (strength would be handled in audio processing)
        return beats
    }
    
    private fun softenRhythm(beats: List<Float>, intensity: Float): List<Float> {
        // Add slight timing variations to make rhythm less rigid
        return beats.map { beat ->
            val variation = (Random.nextFloat() - 0.5f) * 0.05f * intensity
            beat + variation
        }
    }
    
    private fun makeBeatsDriving(beats: List<Float>, intensity: Float): List<Float> {
        // Ensure beats are on a strong grid for driving feel
        return quantizeRhythm(beats, intensity * 0.8f)
    }
    
    private fun createDramaticRhythm(beats: List<Float>, intensity: Float): List<Float> {
        // Add dramatic pauses and emphasis (simplified)
        return beats
    }
    
    private fun createFourOnFloorPattern(beats: List<Float>, intensity: Float): List<Float> {
        // Create or enhance four-on-the-floor pattern
        return quantizeRhythm(beats, intensity)
    }
    
    private fun createMechanicalRhythm(beats: List<Float>, intensity: Float): List<Float> {
        // Make rhythm very precise and mechanical
        return quantizeRhythm(beats, intensity * 1.2f)
    }
    
    private fun relaxRhythm(beats: List<Float>, intensity: Float): List<Float> {
        // Add subtle swing and relaxation
        return beats.mapIndexed { index, beat ->
            val swing = if (index % 2 == 1) 0.02f * intensity else 0f
            beat + swing
        }
    }
    
    private fun createUnconventionalRhythm(beats: List<Float>, intensity: Float): List<Float> {
        // Add random variations for experimental feel
        return beats.map { beat ->
            val variation = (Random.nextFloat() - 0.5f) * 0.2f * intensity
            beat + variation
        }
    }
    
    // Additional processing functions
    
    private fun applySubtleFiltering(spectrum: FloatArray, intensity: Float): FloatArray {
        return applySpectralSmoothing(spectrum, intensity * 0.3f)
    }
    
    private fun applyIndustrialProcessing(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        
        // Add harmonic distortion
        for (i in result.indices) {
            if (result[i] > 0.6f) {
                result[i] = tanh(result[i] * (1f + intensity * 0.4f)).toFloat()
            }
        }
        
        return result
    }
    
    private fun enhancePercussiveElements(spectrum: FloatArray, intensity: Float): FloatArray {
        return enhanceFrequencyRange(spectrum, 0.1f, 0.3f, intensity * 0.5f)
    }
    
    private fun addWarmth(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        
        // Enhance low-mid frequencies for warmth
        return enhanceFrequencyRange(result, 0.1f, 0.4f, intensity * 0.3f)
    }
    
    private fun applyGentleRolloff(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        
        // Gentle high-frequency roll-off
        for (i in (spectrum.size * 0.7f).toInt() until spectrum.size) {
            val rolloffFactor = 1f - (i - spectrum.size * 0.7f) / (spectrum.size * 0.3f) * intensity * 0.3f
            result[i] *= rolloffFactor.coerceAtLeast(0.3f)
        }
        
        return result
    }
    
    private fun applyRandomSpectralModifications(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        
        for (i in result.indices) {
            val randomFactor = 1f + (Random.nextFloat() - 0.5f) * intensity * 0.4f
            result[i] *= randomFactor
        }
        
        return result
    }
    
    private fun addGlitchEffects(spectrum: FloatArray, intensity: Float): FloatArray {
        val result = spectrum.copyOf()
        
        // Random spectral gaps and spikes
        for (i in result.indices) {
            if (Random.nextFloat() < intensity * 0.1f) {
                result[i] *= if (Random.nextBoolean()) 0.1f else 2.0f
            }
        }
        
        return result
    }
}

data class AudioFeatures(
    val spectrum: FloatArray,
    val beats: List<Float>,
    val harmonicContent: Float = 0.5f,
    val bassContent: Float = 0.5f,
    val energy: Float = 0.5f,
    val ambientQuality: Float = 0f,
    val aggressiveness: Float = 0f,
    val grooveQuality: Float = 0f,
    val mechanicalQuality: Float = 0f,
    val warmth: Float = 0f,
    val experimentalFactor: Float = 0f
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as AudioFeatures
        
        if (!spectrum.contentEquals(other.spectrum)) return false
        if (beats != other.beats) return false
        if (harmonicContent != other.harmonicContent) return false
        if (bassContent != other.bassContent) return false
        if (energy != other.energy) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = spectrum.contentHashCode()
        result = 31 * result + beats.hashCode()
        result = 31 * result + harmonicContent.hashCode()
        result = 31 * result + bassContent.hashCode()
        result = 31 * result + energy.hashCode()
        return result
    }
}

