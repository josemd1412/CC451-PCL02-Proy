package com.example.cc451_pcl02_proy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SafeMineColorScheme = darkColorScheme(
    primary = ListeningBlue,
    onPrimary = MatteBlack,
    background = MatteBlack,
    onBackground = TextPrimary,
    surface = Surface1,
    onSurface = TextPrimary,
    surfaceVariant = Surface2,
    onSurfaceVariant = TextSecondary,
)

/** Siempre oscuro y sin color dinámico: la señalización no debe cambiar con el fondo de pantalla. */
@Composable
fun CC451PCL02ProyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SafeMineColorScheme,
        typography = Typography,
        content = content,
    )
}
