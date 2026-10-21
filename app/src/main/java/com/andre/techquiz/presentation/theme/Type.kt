package com.andre.techquiz.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondaryContainer = PrimaryContainer,
    onSecondaryContainer = OnPrimaryContainer,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = AppSurface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = TextMuted,
    outline = OutlineColor,
    error = WrongColor,
    errorContainer = WrongContainer
)

@Composable
fun TechquizTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}