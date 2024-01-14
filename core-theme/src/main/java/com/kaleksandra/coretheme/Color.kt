package com.kaleksandra.coretheme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val primary = Color(0xFF3246F3)
private val textSecondary = Color(0xFF727272)
private val lightPrimaryContainer = Color(0xFFF8F8F8)
private val darkPrimaryContainer = Color(0xFF221E33)

internal val DarkColors = darkColorScheme(
    primary = primary,
    tertiary = textSecondary,
    onSecondaryContainer = darkPrimaryContainer
    //TODO: Add dark colors theme
)

internal val LightColors = lightColorScheme(
    primary = primary,
    tertiary = textSecondary,
    onSecondaryContainer = lightPrimaryContainer
    //TODO: Add light colors theme
)