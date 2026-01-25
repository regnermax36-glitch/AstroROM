package com.neuramusic.presentation.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.neuramusic.presentation.component.NeuralWaveformVisualizer
import com.neuramusic.presentation.component.QuantumSeekBar
import com.neuramusic.presentation.viewmodel.AIEnhancedMusicViewModel
import com.neuramusic.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayingScreen(
    musicViewModel: AIEnhancedMusicViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    var isExpanded by remember { mutableStateOf(false) }
    var showLyrics by remember { mutableStateOf(false) }
    var showVisualizer by remember { mutableStateOf(true) }
    
    // Rotation animation for album art
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    // Pulsing animation for play button
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NeuraGradientStart.copy(alpha = 0.3f),
                        NeuraGradientEnd.copy(alpha = 0.5f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        // Background blur effect
        AsyncImage(
            model = null, // Current song album art
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(50.dp)
                .scale(1.2f),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Top Bar with Neural Design
            NeuralTopBar(
                onNavigateBack = onNavigateBack,
                onToggleLyrics = { showLyrics = !showLyrics },
                onToggleVisualizer = { showVisualizer = !showVisualizer },
                showLyrics = showLyrics,
                showVisualizer = showVisualizer
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Album Art with Quantum Effects
                QuantumAlbumArt(
                    albumArt = null, // Current song album art
                    isPlaying = false, // From viewModel
                    rotation = rotation,
                    modifier = Modifier.size(280.dp)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Song Info with Neural Glow
                NeuralSongInfo(
                    title = "Sample Song", // From viewModel
                    artist = "Sample Artist", // From viewModel
                    album = "Sample Album" // From viewModel
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Waveform Visualizer (Revolutionary Feature)
                AnimatedVisibility(
                    visible = showVisualizer,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    NeuralWaveformVisualizer(
                        isPlaying = false, // From viewModel
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Quantum Seek Bar
                QuantumSeekBar(
                    progress = 0.3f, // From viewModel
                    onSeek = { /* Handle seek */ },
                    currentTime = "1:23", // From viewModel
                    totalTime = "4:56", // From viewModel
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Neural Control Buttons
                NeuralControlButtons(
                    isPlaying = false, // From viewModel
                    onPreviousClick = { /* Handle previous */ },
                    onPlayPauseClick = { 
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        /* Handle play/pause */
                    },
                    onNextClick = { /* Handle next */ },
                    onShuffleClick = { /* Handle shuffle */ },
                    onRepeatClick = { /* Handle repeat */ },
                    isShuffleEnabled = false, // From viewModel
                    repeatMode = 0, // From viewModel
                    pulseScale = pulseScale
                )
            }
        }
        
        // Floating Lyrics Panel (Revolutionary Feature)
        AnimatedVisibility(
            visible = showLyrics,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            NeuralLyricsPanel(
                lyrics = "Sample lyrics would appear here...", // From viewModel
                onDismiss = { showLyrics = false }
            )
        }
    }
}

@Composable
private fun NeuralTopBar(
    onNavigateBack: () -> Unit,
    onToggleLyrics: () -> Unit,
    onToggleVisualizer: () -> Unit,
    showLyrics: Boolean,
    showVisualizer: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        
        Text(
            text = "Now Playing",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Row {
            IconButton(
                onClick = onToggleVisualizer,
                modifier = Modifier
                    .background(
                        color = if (showVisualizer) NeuraPrimary.copy(alpha = 0.2f) 
                               else MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.GraphicEq,
                    contentDescription = "Visualizer",
                    tint = if (showVisualizer) NeuraPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
            
            IconButton(
                onClick = onToggleLyrics,
                modifier = Modifier
                    .background(
                        color = if (showLyrics) NeuraPrimary.copy(alpha = 0.2f) 
                               else MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Lyrics,
                    contentDescription = "Lyrics",
                    tint = if (showLyrics) NeuraPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun QuantumAlbumArt(
    albumArt: String?,
    isPlaying: Boolean,
    rotation: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Outer glow ring
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            NeuraPrimary.copy(alpha = 0.3f),
                            NeuraPrimary.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        
        // Album art with rotation
        Card(
            modifier = Modifier
                .size(240.dp)
                .rotate(if (isPlaying) rotation else 0f),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            AsyncImage(
                model = albumArt,
                contentDescription = "Album Art",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        // Center play indicator
        if (isPlaying) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = NeuraPrimary,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
private fun NeuralSongInfo(
    title: String,
    artist: String,
    album: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = artist,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = album,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun NeuralControlButtons(
    isPlaying: Boolean,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
    isShuffleEnabled: Boolean,
    repeatMode: Int,
    pulseScale: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main controls
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPreviousClick,
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            
            // Main play/pause button with quantum effects
            IconButton(
                onClick = onPlayPauseClick,
                modifier = Modifier
                    .size(80.dp)
                    .scale(pulseScale)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                NeuraPrimary,
                                NeuraSecondary
                            )
                        ),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
            
            IconButton(
                onClick = onNextClick,
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Secondary controls
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onShuffleClick,
                modifier = Modifier
                    .background(
                        color = if (isShuffleEnabled) NeuraPrimary.copy(alpha = 0.2f) 
                               else MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = "Shuffle",
                    tint = if (isShuffleEnabled) NeuraPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
            
            IconButton(onClick = { /* Handle favorite */ }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            
            IconButton(onClick = { /* Handle queue */ }) {
                Icon(
                    imageVector = Icons.Default.QueueMusic,
                    contentDescription = "Queue",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            
            IconButton(
                onClick = onRepeatClick,
                modifier = Modifier
                    .background(
                        color = if (repeatMode > 0) NeuraPrimary.copy(alpha = 0.2f) 
                               else MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = when (repeatMode) {
                        1 -> Icons.Default.Repeat
                        2 -> Icons.Default.RepeatOne
                        else -> Icons.Default.Repeat
                    },
                    contentDescription = "Repeat",
                    tint = if (repeatMode > 0) NeuraPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun NeuralLyricsPanel(
    lyrics: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Handle bar
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(2.dp)
                    )
                    .align(Alignment.CenterHorizontally)
                    .clickable { onDismiss() }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Lyrics",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = lyrics,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}
