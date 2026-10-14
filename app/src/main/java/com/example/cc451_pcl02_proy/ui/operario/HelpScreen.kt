package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.ListeningBlue
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalRed
import com.example.cc451_pcl02_proy.ui.theme.Surface1
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

private data class Example(val title: String, val say: String)

private val examples = listOf(
    Example("Reportar un riesgo", "“Reporto derrame de aceite en zona 3”"),
    Example("Reportar otro riesgo", "“Reporto cable pelado en zona 2”"),
    Example("Pedir ayuda urgente", "“SafeMine emergencia”"),
)

/** Ayuda contextual "¿Qué puedo decir?" para esta pantalla de espera (HU-12). Frases de 12 palabras o menos. */
@Composable
fun HelpScreen(onBack: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ZoneHeader(Modifier.fillMaxWidth())
        Text("¿QUÉ PUEDO DECIR?", style = MaterialTheme.typography.headlineLarge, color = ListeningBlue)

        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            examples.forEach { ex ->
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Surface1).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(ex.title, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                    Text(ex.say, style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                }
            }
            Text(
                "Empieza con “SafeMine”. Luego di qué pasó y en qué zona.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
            )
        }
        BigButton("ESCUCHAR AYUDA", {}, Modifier.fillMaxWidth(), container = ListeningBlue, content = MatteBlack)
        BigButton("VOLVER", onBack, Modifier.fillMaxWidth())
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun HelpPreview() = CC451PCL02ProyTheme { HelpScreen({}) }
