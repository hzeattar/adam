package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CyberEmeraldPrimary,
    onPrimary = Color.Black,
    primaryContainer = TechCardDark,
    onPrimaryContainer = CircuitCyan,
    secondary = CircuitCyan,
    onSecondary = Color.Black,
    secondaryContainer = TechCardBorder,
    background = DarkBg,
    surface = TechCardDark,
    onBackground = TextHigh,
    onSurface = TextHigh,
    surfaceVariant = TechCardDark,
    onSurfaceVariant = TextMedium,
    outline = TechCardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00897B),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2F1),
    onPrimaryContainer = Color(0xFF004D40),
    secondary = Color(0xFF0288D1),
    onSecondary = Color.White,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569),
    outline = Color(0xFFCBD5E1)
)

@Composable
fun ReViveTheme(
    darkTheme: Boolean = true, // Premium high-tech e-waste dark theme by default
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
