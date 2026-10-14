package com.example.cc451_pcl02_proy.ui.supervisor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.cc451_pcl02_proy.domain.ReportStatus
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.SeverityShape
import com.example.cc451_pcl02_proy.ui.components.background
import com.example.cc451_pcl02_proy.ui.components.content
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.ListeningBlue
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalGreen
import com.example.cc451_pcl02_proy.ui.theme.Surface1
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

private val correctiveActions = listOf("Colocar paños absorbentes", "Enviar brigada", "Cortar energía del tablero")

/**
 * Detalle de una alerta (HU-08): transcripción original, protocolo, y los pasos
 * confirmar -> asignar acción -> cerrar. Cada cambio avisa al operario.
 */
@Composable
fun AlertDetailScreen(report: Report, onChange: (Report) -> Unit, onBack: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(report.severity.background).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SeverityShape(report.severity, fill = report.severity.content, glyph = report.severity.background, modifier = Modifier.size(64.dp))
            Column {
                Text(report.severity.word, style = MaterialTheme.typography.headlineLarge, color = report.severity.content)
                Text(report.type, style = MaterialTheme.typography.titleLarge, color = report.severity.content)
            }
        }

        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Block("Zona y hora", "Zona ${report.zone} · ${report.time}")
            Block("Transcripción original", "“${report.transcript}”")
            if (report.protocol.isNotEmpty()) Block("Protocolo sugerido", report.protocol.joinToString(" "))
            Block("Estado", report.status.label)
            report.action?.let { Block("Acción asignada", it) }

            if (report.status == ReportStatus.IN_PROGRESS && report.action == null) {
                Text("Asignar acción", style = MaterialTheme.typography.titleLarge, color = TextSecondary)
                correctiveActions.forEach { action ->
                    BigButton(action, { onChange(report.copy(action = action)) }, Modifier.fillMaxWidth())
                }
            }
        }

        when (report.status) {
            ReportStatus.NEW -> BigButton(
                "CONFIRMAR", { onChange(report.copy(status = ReportStatus.IN_PROGRESS)) },
                Modifier.fillMaxWidth(), container = ListeningBlue, content = MatteBlack,
            )
            ReportStatus.IN_PROGRESS -> BigButton(
                "CERRAR REPORTE", { onChange(report.copy(status = ReportStatus.CLOSED)) },
                Modifier.fillMaxWidth(), container = SignalGreen, content = MatteBlack,
            )
            ReportStatus.CLOSED -> Text("Cerrado. El operario recibió las gracias de su cuadrilla.", style = MaterialTheme.typography.bodyLarge, color = SignalGreen)
        }
        BigButton("VOLVER", onBack, Modifier.fillMaxWidth())
    }
}

@Composable
private fun Block(title: String, body: String) {
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Surface1).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        Text(body, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun DetailPreview() = CC451PCL02ProyTheme { AlertDetailScreen(MockData.supervisorReports[0], {}, {}) }
