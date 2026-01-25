package com.neuramusic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Glass Neural Colors
val GlassNeuralPrimary = Color(0xFF6366F1)
val GlassNeuralSecondary = Color(0xFF8B5CF6)
val GlassNeuralAccent = Color(0xFFEC4899)
val GlassNeuralTertiary = Color(0xFF06B6D4)

// Glass Background Colors
val GlassBackgroundDark = Color(0xFF0A0A0F)
val GlassSurface = Color(0xFF1A1A2E)
val GlassCard = Color(0x1AFFFFFF)

// Neural Glow Colors
val NeuralGlow1 = Color(0xFF667EEA)
val NeuralGlow2 = Color(0xFF764BA2)
val NeuralGlow3 = Color(0xFFF093FB)
val NeuralGlow4 = Color(0xFFF5576C)

// AI Accent Colors
val AIBlue = Color(0xFF00D4FF)
val AIPurple = Color(0xFF9D4EDD)
val AIPink = Color(0xFFFF006E)
val AIGreen = Color(0xFF39FF14)
val AIYellow = Color(0xFFFFFF00)

private val GlassDarkColorScheme = darkColorScheme(
    primary = GlassNeuralPrimary,
    secondary = GlassNeuralSecondary,
    tertiary = GlassNeuralTertiary,
    background = GlassBackgroundDark,
    surface = GlassSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val GlassLightColorScheme = lightColorScheme(
    primary = GlassNeuralPrimary,
    secondary = GlassNeuralSecondary,
    tertiary = GlassNeuralTertiary,
    background = Color(0xFFF8F9FF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun GlassNeuraMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        GlassDarkColorScheme
    } else {
        GlassLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
