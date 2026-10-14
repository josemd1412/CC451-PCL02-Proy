package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.data.MockData
import com.example.cc451_pcl02_proy.domain.Report
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalGreen
import com.example.cc451_pcl02_proy.ui.theme.Surface1
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

/** La app lee el resumen y pide confirmar o corregir antes de enviar (HU-03). */
@Composable
fun ConfirmScreen(report: Report, onConfirm: () -> Unit, onCorrect: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ZoneHeader(Modifier.fillMaxWidth())

        if (report.byVoice) {
            Text("Te escuché:", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Text("“${report.transcript}”", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        } else {
            Text("Elegiste:", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        }

        Column(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Surface1).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(report.type.uppercase(), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Text("Zona ${report.zone} · ${report.severity.word}", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
        }

        Text("¿Envío?", style = MaterialTheme.typography.displayLarge, color = TextPrimary)

        Row(Modifier.weight(1f), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            BigButton("CORREGIR", onCorrect, Modifier.weight(1f).height(120.dp))
            BigButton("SÍ, ENVIAR", onConfirm, Modifier.weight(1.4f).height(120.dp), container = SignalGreen, content = MatteBlack)
        }
        Text(
            "Di “sí” para enviar, o toca el botón",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
        )
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun ConfirmPreview() {
    CC451PCL02ProyTheme { ConfirmScreen(MockData.voiceDemos[0], {}, {}) }
}
