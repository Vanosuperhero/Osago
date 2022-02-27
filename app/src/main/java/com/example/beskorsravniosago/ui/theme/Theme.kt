package com.example.beskorsravniosago.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Accent_Blue_100b,
    primaryVariant = primaryVariant,
    secondary = Main_Main_100b,
    background = Main_Main_60b,
    surface = Main_Main_30b,
    onPrimary = Main_Main_20b,
    onSecondary = Main_Main_06b,
    onBackground = almostBlack_b,
    onSurface = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Accent_Blue_100,
    primaryVariant = Color.White,
    secondary = Main_Main_100,
    background = Main_Main_60,
    surface = Main_Main_30,
    onPrimary = Main_Main_20,
    onSecondary = Main_Main_06,
    onBackground = almostBlack,
    onSurface = Color.Black,
)

@Composable
fun BeskorSravniOsagoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}