package com.neuramusic.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neuramusic.presentation.component.glass.AIVisualizerCard
import com.neuramusic.presentation.component.glass.GlassCard
import com.neuramusic.presentation.component.glass.NeuralWaveVisualizer
import com.neuramusic.presentation.viewmodel.AIEnhancedMusicViewModel
import com.neuramusic.ui.theme.*

@Composable
fun HomeScreen(
    musicViewModel: AIEnhancedMusicViewModel,
    onNavigateToNowPlaying: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPlaying by musicViewModel.isPlaying.collectAsState()
    val currentSong by musicViewModel.currentSong.collectAsState()
    val currentArtist by musicViewModel.currentArtist.collectAsState()
    val aiRecommendations by musicViewModel.aiRecommendations.collectAsState()
    val aiMoodAnalysis by musicViewModel.aiMoodAnalysis.collectAsState()
    val smartPlaylistSuggestion by musicViewModel.smartPlaylistSuggestion.collectAsState()
    val neuralVisualizerData by musicViewModel.neuralVisualizerData.collectAsState()
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            // Header with AI greeting
            GlassCard {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "NeuraMusic 2.0",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = "Glass AI Edition • Mood: $aiMoodAnalysis",
                        style = MaterialTheme.typography.bodyLarge,
                        color = GlassNeuralAccent,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
        
        item {
            // Now Playing Glass Card
            if (currentSong != null) {
                GlassCard(
                    modifier = Modifier.clickable { onNavigateToNowPlaying() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Album art with neural glow
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            GlassNeuralPrimary,
                                            GlassNeuralSecondary,
                                            GlassNeuralAccent
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        // Song info
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = currentSong ?: "No song selected",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = currentArtist ?: "Unknown artist",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            
                            // AI mood indicator
                            Text(
                                text = "AI Analysis: $aiMoodAnalysis",
                                style = MaterialTheme.typography.bodySmall,
                                color = GlassNeuralTertiary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        
                        // Play/pause button
                        IconButton(
                            onClick = { musicViewModel.playPause() }
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = GlassNeuralPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
        
        item {
            // Neural Visualizer
            if (isPlaying && neuralVisualizerData.isNotEmpty()) {
                AIVisualizerCard {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Neural Visualizer",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = "Visualizer",
                                tint = AIBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        NeuralWaveVisualizer(
                            data = neuralVisualizerData,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )
                    }
                }
            }
        }
        
        item {
            // Smart Playlist Suggestion
            smartPlaylistSuggestion?.let { suggestion ->
                GlassCard {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Suggestion",
                            tint = AIYellow,
                            modifier = Modifier.size(32.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "AI Suggests",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            Text(
                                text = suggestion,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        
                        IconButton(
                            onClick = { musicViewModel.refreshAIRecommendations() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = GlassNeuralPrimary
                            )
                        }
                    }
                }
            }
        }
        
        item {
            // AI Recommendations
            Text(
                text = "AI Recommendations",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(aiRecommendations) { (song, artist) ->
                    GlassCard(
                        modifier = Modifier
                            .width(200.dp)
                            .clickable {
                                musicViewModel.loadSong(song, artist)
                                musicViewModel.playPause()
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            // Mini album art
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                NeuralGlow1,
                                                NeuralGlow2,
                                                NeuralGlow3
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Music",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = song,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1
                            )
                            
                            Text(
                                text = artist,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

