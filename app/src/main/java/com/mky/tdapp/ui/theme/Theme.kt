package com.mky.tdapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BrandPrimaryDark,
    secondary = BrandSecondaryDark,

    background = BgPrimaryDark,
    surface = BgSecondaryDark,

    onPrimary = Color.Black,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,

    outline = BorderPrimaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    secondary = BrandSecondary,

    background = BgPrimary,
    surface = BgSecondary,

    onPrimary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,

    outline = BorderPrimary
)

@Composable
fun TDAPPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}