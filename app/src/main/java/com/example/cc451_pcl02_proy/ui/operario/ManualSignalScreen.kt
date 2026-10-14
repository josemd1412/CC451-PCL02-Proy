package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.domain.Severity
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.SeverityShape
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.components.background
import com.example.cc451_pcl02_proy.ui.components.content
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary

private val meaning = mapOf(
    Severity.OK to "Todo bien",
    Severity.CAUTION to "Advertencia",
    Severity.DANGER to "Peligro inmediato",
)

/**
 * Semáforo manual para quien no puede hablar (dificultad del habla, o ruido que impide la voz):
 * tres botones enormes, uno por nivel. Mismo lenguaje visual que el semáforo de resultado:
 * color + forma + palabra. [onPick] recibe el nivel elegido; luego se confirma el envío.
 */
@Composable
fun ManualSignalScreen(onPick: (Severity) -> Unit, onBack: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ZoneHeader(Modifier.fillMaxWidth())
        Text("¿CÓMO ESTÁ TU ZONA?", style = MaterialTheme.typography.headlineLarge, color = TextPrimary)

        // De arriba abajo: verde, ámbar, rojo (como un semáforo vial).
        Severity.entries.forEach { severity ->
            Row(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(severity.background)
                    .clickable(role = Role.Button) { onPick(severity) }
                    .semantics { contentDescription = "${severity.word}: ${meaning.getValue(severity)}" }
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                SeverityShape(severity, fill = severity.content, glyph = severity.background, modifier = Modifier.size(88.dp))
                Column {
                    Text(severity.word, style = MaterialTheme.typography.headlineLarge, color = severity.content)
                    Text(meaning.getValue(severity), style = MaterialTheme.typography.titleLarge, color = severity.content)
                }
            }
        }
        BigButton("VOLVER", onBack, Modifier.fillMaxWidth())
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun ManualSignalPreview() = CC451PCL02ProyTheme { ManualSignalScreen({}, {}) }
