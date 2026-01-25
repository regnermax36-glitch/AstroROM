package com.neuramusic.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class AIEnhancedMusicViewModel : SimpleMusicViewModel() {
    
    // Additional AI-specific state (duration not in parent)
    private val _duration = MutableStateFlow(0f)
    val duration: StateFlow<Float> = _duration.asStateFlow()
    
    // AI Features
    private val _aiRecommendations = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val aiRecommendations: StateFlow<List<Pair<String, String>>> = _aiRecommendations.asStateFlow()
    
    private val _neuralVisualizerData = MutableStateFlow<List<Float>>(emptyList())
    val neuralVisualizerData: StateFlow<List<Float>> = _neuralVisualizerData.asStateFlow()
    
    private val _aiMoodAnalysis = MutableStateFlow("Energetic")
    val aiMoodAnalysis: StateFlow<String> = _aiMoodAnalysis.asStateFlow()
    
    private val _smartPlaylistSuggestion = MutableStateFlow<String?>(null)
    val smartPlaylistSuggestion: StateFlow<String?> = _smartPlaylistSuggestion.asStateFlow()
    
    // Glass theme states
    private val _glassBlurIntensity = MutableStateFlow(0.5f)
    val glassBlurIntensity: StateFlow<Float> = _glassBlurIntensity.asStateFlow()
    
    private val _glassOpacity = MutableStateFlow(0.2f)
    val glassOpacity: StateFlow<Float> = _glassOpacity.asStateFlow()
    
    // AI-themed song library
    private val aiSongLibrary = listOf(
        "Quantum Beats" to "Neural Network",
        "Digital Consciousness" to "AI Symphony", 
        "Synthetic Dreams" to "Machine Learning",
        "Binary Emotions" to "Deep Learning",
        "Algorithmic Love" to "Neural Composer",
        "Data Stream" to "AI Orchestra",
        "Virtual Reality" to "Synthetic Mind",
        "Cyber Meditation" to "Digital Zen",
        "Neural Pathways" to "Brain Wave",
        "Artificial Harmony" to "Code Symphony"
    )
    
    init {
        // Initialize AI features
        refreshAIRecommendations()
        generateNeuralVisualizerData()
        updateSmartPlaylistSuggestion()
    }
    
    // Override parent methods to add AI functionality
    override fun playPause() {
        super.playPause()
        viewModelScope.launch {
            if (isPlaying.value) {
                generateNeuralVisualizerData()
                // Load default song if none selected
                if (currentSong.value == null) {
                    val defaultSong = aiSongLibrary.random()
                    loadSong(defaultSong.first, defaultSong.second)
                }
            }
        }
    }
    
    fun loadSong(title: String, artist: String) {
        viewModelScope.launch {
            // Now we can access protected fields directly
            _currentSong.value = title
            _currentArtist.value = artist
            
            // Generate realistic duration (2-6 minutes)
            _duration.value = (120 + Random.nextInt(240)).toFloat()
            
            // AI mood analysis based on song title
            _aiMoodAnalysis.value = when {
                title.contains("Quantum") || title.contains("Neural") -> "Futuristic"
                title.contains("Digital") || title.contains("Cyber") -> "Electronic"
                title.contains("Love") || title.contains("Harmony") -> "Romantic"
                title.contains("Meditation") || title.contains("Dreams") -> "Peaceful"
                else -> "Energetic"
            }
            
            refreshAIRecommendations()
        }
    }
    
    override fun seekTo(position: Float) {
        super.seekTo(position)
        // Update neural visualizer based on position
        generateNeuralVisualizerData()
    }
    
    fun skipNext() {
        viewModelScope.launch {
            // Select next from AI recommendations
            val recommendations = _aiRecommendations.value
            if (recommendations.isNotEmpty()) {
                val nextSong = recommendations.random()
                loadSong(nextSong.first, nextSong.second)
            }
        }
    }
    
    fun skipPrevious() {
        viewModelScope.launch {
            // Reset progress to beginning
            seekTo(0f)
        }
    }
    
    override fun toggleShuffle() {
        viewModelScope.launch {
            // Access protected fields directly
            _isShuffleEnabled.value = !_isShuffleEnabled.value
            
            // Regenerate recommendations based on shuffle mode
            refreshAIRecommendations()
        }
    }
    
    override fun toggleRepeat() {
        viewModelScope.launch {
            // Access protected fields directly
            _repeatMode.value = (_repeatMode.value + 1) % 3 // Cycle through 0, 1, 2
        }
    }
    
    fun refreshAIRecommendations() {
        viewModelScope.launch {
            // Generate 6 random recommendations from the AI library
            val shuffled = aiSongLibrary.shuffled()
            _aiRecommendations.value = shuffled.take(6)
            
            // Update smart playlist suggestion
            updateSmartPlaylistSuggestion()
        }
    }
    
    private fun generateNeuralVisualizerData() {
        viewModelScope.launch {
            // Generate realistic neural wave data
            val data = mutableListOf<Float>()
            for (i in 0 until 50) {
                val baseValue = Random.nextFloat() * 0.8f + 0.1f
                val variation = (Random.nextFloat() - 0.5f) * 0.3f
                data.add((baseValue + variation).coerceIn(0f, 1f))
            }
            _neuralVisualizerData.value = data
        }
    }
    
    private fun updateSmartPlaylistSuggestion() {
        viewModelScope.launch {
            val suggestions = listOf(
                "Create 'AI Chill' playlist",
                "Generate 'Neural Beats' mix",
                "Build 'Synthetic Vibes' collection",
                "Curate 'Digital Dreams' playlist",
                "Assemble 'Quantum Rhythms' mix"
            )
            _smartPlaylistSuggestion.value = suggestions.random()
        }
    }
    
    fun adjustGlassEffect(blurIntensity: Float, opacity: Float) {
        viewModelScope.launch {
            _glassBlurIntensity.value = blurIntensity.coerceIn(0f, 1f)
            _glassOpacity.value = opacity.coerceIn(0f, 0.5f)
        }
    }
    
    // Expose shuffle and repeat mode from parent
    val shuffleMode: StateFlow<Boolean> get() = isShuffleEnabled
}
