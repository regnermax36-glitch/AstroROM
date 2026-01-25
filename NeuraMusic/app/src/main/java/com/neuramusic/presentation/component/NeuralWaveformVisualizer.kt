package com.neuramusic.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.neuramusic.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

@Composable
fun NeuralWaveformVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    barCount: Int = 60,
    animationDuration: Int = 100
) {
    val density = LocalDensity.current
    
    // Generate random waveform data
    var waveformData by remember { mutableStateOf(generateWaveformData(barCount)) }
    
    // Animation for the waveform bars
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    
    // Animate the waveform when playing
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            waveformData = generateWaveformData(barCount)
            delay(animationDuration.toLong())
        }
    }
    
    // Color animation
    val colorPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "color_phase"
    )
    
    Canvas(
        modifier = modifier.fillMaxWidth()
    ) {
        drawNeuralWaveform(
            waveformData = waveformData,
            isPlaying = isPlaying,
            colorPhase = colorPhase,
            barCount = barCount
        )
    }
}

private fun DrawScope.drawNeuralWaveform(
    waveformData: List<Float>,
    isPlaying: Boolean,
    colorPhase: Float,
    barCount: Int
) {
    val barWidth = size.width / barCount
    val centerY = size.height / 2f
    val maxBarHeight = size.height * 0.8f
    
    waveformData.forEachIndexed { index, amplitude ->
        val x = index * barWidth + barWidth / 2f
        val barHeight = if (isPlaying) amplitude * maxBarHeight else maxBarHeight * 0.1f
        
        // Create gradient colors based on position and time
        val hue = (index.toFloat() / barCount * 360f + colorPhase * 57.3f) % 360f
        val color = Color.hsv(hue, 0.8f, 1f, alpha = 0.8f)
        
        // Neural glow effect
        val glowColor = color.copy(alpha = 0.3f)
        
        // Draw glow
        drawLine(
            color = glowColor,
            start = Offset(x, centerY - barHeight / 2f - 4.dp.toPx()),
            end = Offset(x, centerY + barHeight / 2f + 4.dp.toPx()),
            strokeWidth = barWidth * 1.5f,
            cap = StrokeCap.Round
        )
        
        // Draw main bar
        drawLine(
            color = color,
            start = Offset(x, centerY - barHeight / 2f),
            end = Offset(x, centerY + barHeight / 2f),
            strokeWidth = barWidth * 0.8f,
            cap = StrokeCap.Round
        )
        
        // Draw highlight
        val highlightColor = Color.White.copy(alpha = 0.6f)
        drawLine(
            color = highlightColor,
            start = Offset(x, centerY - barHeight / 2f),
            end = Offset(x, centerY - barHeight / 2f + barHeight * 0.3f),
            strokeWidth = barWidth * 0.3f,
            cap = StrokeCap.Round
        )
    }
}

private fun generateWaveformData(barCount: Int): List<Float> {
    return (0 until barCount).map {
        // Generate more realistic waveform data
        val baseAmplitude = Random.nextFloat()
        val frequency = it.toFloat() / barCount * 4 * PI
        val wave = sin(frequency).toFloat() * 0.3f + 0.7f
        (baseAmplitude * wave).coerceIn(0.1f, 1f)
    }
}

@Composable
fun QuantumSeekBar(
    progress: Float,
    onSeek: (Float) -> Unit,
    currentTime: String,
    totalTime: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // Time labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentTime,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = totalTime,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Custom quantum seek bar
        val trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            val trackHeight = 4.dp.toPx()
            val progressWidth = size.width * progress
            
            // Background track
            drawRoundRect(
                color = trackColor,
                size = androidx.compose.ui.geometry.Size(size.width, trackHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(trackHeight / 2f)
            )
            
            // Progress track with gradient
            val gradient = Brush.horizontalGradient(
                colors = listOf(NeuraPrimary, NeuraSecondary, NeuraAccent),
                endX = progressWidth
            )
            
            drawRoundRect(
                brush = gradient,
                size = androidx.compose.ui.geometry.Size(progressWidth, trackHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(trackHeight / 2f)
            )
            
            // Quantum glow effect
            drawRoundRect(
                color = NeuraPrimary.copy(alpha = 0.3f),
                size = androidx.compose.ui.geometry.Size(progressWidth, trackHeight * 2f),
                topLeft = Offset(0f, -trackHeight / 2f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(trackHeight)
            )
        }
    }
}
