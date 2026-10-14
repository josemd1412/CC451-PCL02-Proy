package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalAmber
import com.example.cc451_pcl02_proy.ui.theme.SignalGreen
import com.example.cc451_pcl02_proy.ui.theme.Surface1
import com.example.cc451_pcl02_proy.ui.theme.Surface2
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

private data class Badge(val name: String, val earned: Boolean)

private val badges = listOf(
    Badge("Ojo atento", true),
    Badge("Voz clara", true),
    Badge("Cuida al equipo", true),
    Badge("Turno sin incidentes", false),
)

/**
 * Nivel reflexivo de Norman: orgullo de cuidar al equipo. Meta colectiva por cuadrilla,
 * sin ranking individual para no incentivar reportes falsos (RF-16, RNF-16).
 */
@Composable
fun CrewScreen(onBack: () -> Unit) {
    val done = 18
    val goal = 25

    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ZoneHeader(Modifier.fillMaxWidth())

        Text(
            "Gracias, tu reporte protege a tu cuadrilla",
            style = MaterialTheme.typography.headlineLarge,
            color = SignalGreen,
        )

        Column(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Surface1).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Meta de la cuadrilla esta semana", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Text("$done de $goal reportes", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
            Box(Modifier.fillMaxWidth().height(28.dp).clip(RoundedCornerShape(14.dp)).background(Surface2)) {
                Box(Modifier.fillMaxHeight().fillMaxWidth(done / goal.toFloat()).background(SignalGreen))
            }
        }

        Text("Tus insignias", style = MaterialTheme.typography.titleLarge, color = TextSecondary)
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            badges.chunked(2).forEach { pair ->
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pair.forEach { badge ->
                        Box(
                            Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (badge.earned) SignalAmber else Surface1)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                badge.name.uppercase(),
                                style = MaterialTheme.typography.titleLarge,
                                color = if (badge.earned) MatteBlack else TextSecondary,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
        BigButton("VOLVER", onBack, Modifier.fillMaxWidth())
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun CrewPreview() = CC451PCL02ProyTheme { CrewScreen({}) }
