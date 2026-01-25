package com.neuramusic.presentation.component.glass

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.neuramusic.ui.theme.*
import kotlin.math.*

@Composable
fun NeuralWaveVisualizer(
    data: List<Float>,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    
    val animatedPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )
    
    val animatedAmplitude by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "amplitude"
    )
    
    Canvas(
        modifier = modifier
    ) {
        if (data.isNotEmpty()) {
            drawNeuralWaves(
                data = data,
                phase = animatedPhase,
                amplitude = animatedAmplitude,
                size = size
            )
        }
    }
}

private fun DrawScope.drawNeuralWaves(
    data: List<Float>,
    phase: Float,
    amplitude: Float,
    size: androidx.compose.ui.geometry.Size
) {
    val width = size.width
    val height = size.height
    val centerY = height / 2
    
    // Neural wave colors
    val waveColors = listOf(
        AIBlue,
        AIPurple,
        AIPink,
        AIGreen,
        AIYellow
    )
    
    // Draw multiple neural wave layers
    waveColors.forEachIndexed { index, color ->
        val path = Path()
        val points = mutableListOf<Offset>()
        
        // Generate wave points
        for (i in 0..data.size) {
            val x = (i.toFloat() / data.size) * width
            val dataIndex = i.coerceAtMost(data.size - 1)
            val baseAmplitude = data[dataIndex] * amplitude
            
            // Create neural wave pattern with phase offset
            val phaseOffset = phase + (index * PI.toFloat() / 3)
            val frequency = 2 + index * 0.5f
            val waveY = centerY + baseAmplitude * height * 0.3f * 
                sin(frequency * (x / width) * 2 * PI.toFloat() + phaseOffset)
            
            points.add(Offset(x, waveY))
        }
        
        // Create smooth path through points
        if (points.isNotEmpty()) {
            path.moveTo(points[0].x, points[0].y)
            
            for (i in 1 until points.size) {
                val current = points[i]
                val previous = points[i - 1]
                
                // Create smooth curves between points
                val controlX = (previous.x + current.x) / 2
                path.quadraticBezierTo(
                    controlX, previous.y,
                    current.x, current.y
                )
            }
            
            // Draw the wave with gradient and glow effect
            val brush = Brush.horizontalGradient(
                colors = listOf(
                    color.copy(alpha = 0.1f),
                    color.copy(alpha = 0.8f),
                    color.copy(alpha = 0.1f)
                )
            )
            
            drawPath(
                path = path,
                brush = brush,
                style = Stroke(
                    width = (3 - index * 0.5f).dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
            
            // Add glow effect
            drawPath(
                path = path,
                color = color.copy(alpha = 0.3f),
                style = Stroke(
                    width = (6 - index * 0.8f).dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
    
    // Draw neural connection nodes
    val nodeCount = min(data.size, 8)
    for (i in 0 until nodeCount) {
        val x = (i.toFloat() / (nodeCount - 1)) * width
        val dataValue = data[i * data.size / nodeCount]
        val nodeY = centerY + dataValue * amplitude * height * 0.2f * sin(phase + i)
        
        // Draw glowing node
        drawCircle(
            color = waveColors[i % waveColors.size].copy(alpha = 0.8f),
            radius = (4 + dataValue * 6) * amplitude,
            center = Offset(x, nodeY)
        )
        
        // Draw node glow
        drawCircle(
            color = waveColors[i % waveColors.size].copy(alpha = 0.3f),
            radius = (8 + dataValue * 12) * amplitude,
            center = Offset(x, nodeY)
        )
    }
}
