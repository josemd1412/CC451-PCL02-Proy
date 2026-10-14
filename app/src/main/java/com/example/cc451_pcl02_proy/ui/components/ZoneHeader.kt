package com.example.cc451_pcl02_proy.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary

/** Encabezado común de las pantallas del operario: "Zona 3 · Turno noche". */
@Composable
fun ZoneHeader(modifier: Modifier = Modifier, color: Color = TextPrimary, text: String = "Zona 3 · Turno noche") {
    Text(text, modifier, color = color, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
}
