package com.airemix.audio

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AudioPlayer(private val context: Context) {
    
    private var exoPlayer: ExoPlayer? = null
    
    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
    
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()
    
    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            updatePlaybackState()
        }
        
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            updatePlaybackState()
        }
        
        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
            _playbackState.value = _playbackState.value.copy(
                error = error.message ?: "Playback error occurred"
            )
        }
    }
    
    fun initialize() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                addListener(playerListener)
            }
        }
    }
    
    fun loadAudio(uri: Uri) {
        exoPlayer?.let { player ->
            val mediaItem = MediaItem.fromUri(uri)
            player.setMediaItem(mediaItem)
            player.prepare()
            updatePlaybackState()
        }
    }
    
    fun play() {
        exoPlayer?.play()
    }
    
    fun pause() {
        exoPlayer?.pause()
    }
    
    fun stop() {
        exoPlayer?.stop()
    }
    
    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }
    
    fun setVolume(volume: Float) {
        exoPlayer?.volume = volume.coerceIn(0f, 1f)
    }
    
    fun release() {
        exoPlayer?.removeListener(playerListener)
        exoPlayer?.release()
        exoPlayer = null
        _playbackState.value = PlaybackState()
    }
    
    fun getCurrentPosition(): Long {
        return exoPlayer?.currentPosition ?: 0L
    }
    
    fun getDuration(): Long {
        return exoPlayer?.duration ?: 0L
    }
    
    fun isPlaying(): Boolean {
        return exoPlayer?.isPlaying ?: false
    }
    
    private fun updatePlaybackState() {
        exoPlayer?.let { player ->
            _playbackState.value = PlaybackState(
                isPlaying = player.isPlaying,
                isLoading = player.playbackState == Player.STATE_BUFFERING,
                isReady = player.playbackState == Player.STATE_READY,
                duration = if (player.duration > 0) player.duration else 0L,
                position = player.currentPosition,
                bufferedPosition = player.bufferedPosition
            )
            _currentPosition.value = player.currentPosition
        }
    }
    
    fun startPositionUpdates() {
        // This would typically be handled by a coroutine that updates position regularly
        // For now, position updates happen through the player listener
    }
    
    fun stopPositionUpdates() {
        // Stop position update coroutine if implemented
    }
}

data class PlaybackState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val isReady: Boolean = false,
    val duration: Long = 0L,
    val position: Long = 0L,
    val bufferedPosition: Long = 0L,
    val error: String? = null
) {
    val progress: Float
        get() = if (duration > 0) position.toFloat() / duration.toFloat() else 0f
    
    val bufferedProgress: Float
        get() = if (duration > 0) bufferedPosition.toFloat() / duration.toFloat() else 0f
    
    val hasError: Boolean
        get() = error != null
    
    val positionFormatted: String
        get() = formatTime(position)
    
    val durationFormatted: String
        get() = formatTime(duration)
    
    private fun formatTime(timeMs: Long): String {
        val totalSeconds = timeMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}

