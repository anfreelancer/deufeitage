/*
 * Theme.kt
 *
 * Copyright 2021 by MicMun
 */
package com.luteapp.deufeitage.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
   primary = PrimaryGrey,
   primaryVariant = PrimaryBlack,
   secondary = Accent,
   background = DarkBackground
)

private val LightColorPalette = lightColors(
   primary = PrimaryGrey,
   primaryVariant = PrimaryBlack,
   secondary = Accent,
   background = LightBackground

   /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DeufeitageTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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
