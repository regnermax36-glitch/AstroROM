package com.airemix.audio

import android.content.Context
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMetadataRetriever
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.ByteBuffer
import kotlin.math.*

class AudioProcessor(private val context: Context) {
    
    companion object {
        private const val SAMPLE_RATE = 44100
        private const val CHANNELS = 2
        private const val BITS_PER_SAMPLE = 16
    }
    
    suspend fun extractAudioData(uri: Uri): AudioData? = withContext(Dispatchers.IO) {
        try {
            val extractor = MediaExtractor()
            extractor.setDataSource(context, uri, null)
            
            var audioTrackIndex = -1
            var format: MediaFormat? = null
            
            // Find audio track
            for (i in 0 until extractor.trackCount) {
                val trackFormat = extractor.getTrackFormat(i)
                val mime = trackFormat.getString(MediaFormat.KEY_MIME)
                if (mime?.startsWith("audio/") == true) {
                    audioTrackIndex = i
                    format = trackFormat
                    break
                }
            }
            
            if (audioTrackIndex == -1 || format == null) {
                return@withContext null
            }
            
            extractor.selectTrack(audioTrackIndex)
            
            val sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE)
            val channelCount = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
            val duration = format.getLong(MediaFormat.KEY_DURATION)
            
            val samples = mutableListOf<Float>()
            val buffer = ByteBuffer.allocate(1024 * 1024) // 1MB buffer
            
            while (true) {
                val sampleSize = extractor.readSampleData(buffer, 0)
                if (sampleSize < 0) break
                
                // Convert bytes to float samples (simplified)
                buffer.rewind()
                for (i in 0 until sampleSize step 2) {
                    if (i + 1 < sampleSize) {
                        val sample = (buffer.get(i).toInt() or (buffer.get(i + 1).toInt() shl 8)).toShort()
                        samples.add(sample.toFloat() / Short.MAX_VALUE)
                    }
                }
                
                extractor.advance()
                buffer.clear()
            }
            
            extractor.release()
            
            AudioData(
                samples = samples.toFloatArray(),
                sampleRate = sampleRate,
                channels = channelCount,
                duration = duration,
                bitrate = format.getInteger(MediaFormat.KEY_BIT_RATE)
            )
            
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    suspend fun analyzeAudio(audioData: AudioData): AudioAnalysis = withContext(Dispatchers.Default) {
        val analysis = AudioAnalysis(
            tempo = estimateTempo(audioData),
            key = estimateKey(audioData),
            loudness = calculateLoudness(audioData),
            spectralCentroid = calculateSpectralCentroid(audioData),
            zeroCrossingRate = calculateZeroCrossingRate(audioData),
            mfcc = calculateMFCC(audioData),
            chroma = calculateChroma(audioData),
            spectralRolloff = calculateSpectralRolloff(audioData)
        )
        analysis
    }
    
    private fun estimateTempo(audioData: AudioData): Float {
        // Simplified tempo estimation using autocorrelation
        val windowSize = audioData.sampleRate / 2 // 0.5 second window
        val hopSize = windowSize / 4
        val samples = audioData.samples
        
        var maxCorrelation = 0f
        var bestTempo = 120f
        
        // Test tempo range from 60 to 200 BPM
        for (bpm in 60..200 step 2) {
            val samplesPerBeat = (audioData.sampleRate * 60f / bpm).toInt()
            var correlation = 0f
            var count = 0
            
            for (i in 0 until samples.size - samplesPerBeat step hopSize) {
                if (i + samplesPerBeat < samples.size) {
                    correlation += samples[i] * samples[i + samplesPerBeat]
                    count++
                }
            }
            
            if (count > 0) {
                correlation /= count
                if (correlation > maxCorrelation) {
                    maxCorrelation = correlation
                    bestTempo = bpm.toFloat()
                }
            }
        }
        
        return bestTempo
    }
    
    private fun estimateKey(audioData: AudioData): String {
        // Simplified key estimation using chroma features
        val chroma = calculateChroma(audioData)
        val keyNames = arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
        
        var maxChroma = 0f
        var keyIndex = 0
        
        for (i in chroma.indices) {
            if (chroma[i] > maxChroma) {
                maxChroma = chroma[i]
                keyIndex = i
            }
        }
        
        return keyNames[keyIndex % 12]
    }
    
    private fun calculateLoudness(audioData: AudioData): Float {
        // RMS loudness calculation
        var sum = 0.0
        for (sample in audioData.samples) {
            sum += sample * sample
        }
        return sqrt(sum / audioData.samples.size).toFloat()
    }
    
    private fun calculateSpectralCentroid(audioData: AudioData): Float {
        // Simplified spectral centroid calculation
        val fft = performFFT(audioData.samples)
        var weightedSum = 0.0
        var magnitudeSum = 0.0
        
        for (i in fft.indices) {
            val magnitude = sqrt(fft[i].real * fft[i].real + fft[i].imag * fft[i].imag)
            val frequency = i * audioData.sampleRate.toFloat() / fft.size
            weightedSum += frequency * magnitude
            magnitudeSum += magnitude
        }
        
        return if (magnitudeSum > 0) (weightedSum / magnitudeSum).toFloat() else 0f
    }
    
    private fun calculateZeroCrossingRate(audioData: AudioData): Float {
        var crossings = 0
        for (i in 1 until audioData.samples.size) {
            if ((audioData.samples[i] >= 0) != (audioData.samples[i - 1] >= 0)) {
                crossings++
            }
        }
        return crossings.toFloat() / audioData.samples.size
    }
    
    private fun calculateMFCC(audioData: AudioData): FloatArray {
        // Simplified MFCC calculation (normally requires mel filter bank)
        val fft = performFFT(audioData.samples)
        val mfcc = FloatArray(13)
        
        // This is a very simplified version - real MFCC requires proper mel scale filtering
        for (i in 0 until 13) {
            var sum = 0.0
            val start = i * fft.size / 26
            val end = (i + 1) * fft.size / 26
            
            for (j in start until end.coerceAtMost(fft.size)) {
                val magnitude = sqrt(fft[j].real * fft[j].real + fft[j].imag * fft[j].imag)
                sum += magnitude
            }
            
            mfcc[i] = ln(sum + 1e-10).toFloat()
        }
        
        return mfcc
    }
    
    private fun calculateChroma(audioData: AudioData): FloatArray {
        val chroma = FloatArray(12)
        val fft = performFFT(audioData.samples)
        
        for (i in fft.indices) {
            val frequency = i * audioData.sampleRate.toFloat() / fft.size
            if (frequency > 80 && frequency < 5000) { // Focus on musical range
                val magnitude = sqrt(fft[i].real * fft[i].real + fft[i].imag * fft[i].imag)
                val pitchClass = (12 * log2(frequency / 440f) + 69).toInt() % 12
                if (pitchClass >= 0) {
                    chroma[pitchClass] += magnitude.toFloat()
                }
            }
        }
        
        // Normalize
        val sum = chroma.sum()
        if (sum > 0) {
            for (i in chroma.indices) {
                chroma[i] /= sum
            }
        }
        
        return chroma
    }
    
    private fun calculateSpectralRolloff(audioData: AudioData): Float {
        val fft = performFFT(audioData.samples)
        var totalMagnitude = 0.0
        
        for (complex in fft) {
            totalMagnitude += sqrt(complex.real * complex.real + complex.imag * complex.imag)
        }
        
        val threshold = totalMagnitude * 0.85 // 85% rolloff
        var cumulativeMagnitude = 0.0
        
        for (i in fft.indices) {
            val magnitude = sqrt(fft[i].real * fft[i].real + fft[i].imag * fft[i].imag)
            cumulativeMagnitude += magnitude
            
            if (cumulativeMagnitude >= threshold) {
                return i * audioData.sampleRate.toFloat() / fft.size
            }
        }
        
        return audioData.sampleRate.toFloat() / 2 // Nyquist frequency
    }
    
    private fun performFFT(samples: FloatArray): Array<Complex> {
        // Simplified FFT implementation (normally would use a proper FFT library)
        val n = samples.size
        val result = Array(n) { Complex(0.0, 0.0) }
        
        for (k in 0 until n) {
            var real = 0.0
            var imag = 0.0
            
            for (t in 0 until n) {
                val angle = -2.0 * PI * k * t / n
                real += samples[t] * cos(angle)
                imag += samples[t] * sin(angle)
            }
            
            result[k] = Complex(real, imag)
        }
        
        return result
    }
    
    private data class Complex(val real: Double, val imag: Double)
}

data class AudioData(
    val samples: FloatArray,
    val sampleRate: Int,
    val channels: Int,
    val duration: Long,
    val bitrate: Int
) {
    val lengthInSeconds: Float
        get() = samples.size.toFloat() / (sampleRate * channels)
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as AudioData
        
        if (!samples.contentEquals(other.samples)) return false
        if (sampleRate != other.sampleRate) return false
        if (channels != other.channels) return false
        if (duration != other.duration) return false
        if (bitrate != other.bitrate) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = samples.contentHashCode()
        result = 31 * result + sampleRate
        result = 31 * result + channels
        result = 31 * result + duration.hashCode()
        result = 31 * result + bitrate
        return result
    }
}

data class AudioAnalysis(
    val tempo: Float,
    val key: String,
    val loudness: Float,
    val spectralCentroid: Float,
    val zeroCrossingRate: Float,
    val mfcc: FloatArray,
    val chroma: FloatArray,
    val spectralRolloff: Float
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as AudioAnalysis
        
        if (tempo != other.tempo) return false
        if (key != other.key) return false
        if (loudness != other.loudness) return false
        if (spectralCentroid != other.spectralCentroid) return false
        if (zeroCrossingRate != other.zeroCrossingRate) return false
        if (!mfcc.contentEquals(other.mfcc)) return false
        if (!chroma.contentEquals(other.chroma)) return false
        if (spectralRolloff != other.spectralRolloff) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = tempo.hashCode()
        result = 31 * result + key.hashCode()
        result = 31 * result + loudness.hashCode()
        result = 31 * result + spectralCentroid.hashCode()
        result = 31 * result + zeroCrossingRate.hashCode()
        result = 31 * result + mfcc.contentHashCode()
        result = 31 * result + chroma.contentHashCode()
        result = 31 * result + spectralRolloff.hashCode()
        return result
    }
}

