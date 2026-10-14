package com.example.cc451_pcl02_proy.ui.supervisor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.data.MockData
import com.example.cc451_pcl02_proy.domain.Report
import com.example.cc451_pcl02_proy.domain.Severity
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.SeverityShape
import com.example.cc451_pcl02_proy.ui.components.background
import com.example.cc451_pcl02_proy.ui.components.content
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.ListeningBlue
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.Surface1
import com.example.cc451_pcl02_proy.ui.theme.Surface2
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

/**
 * Dashboard del turno (RF-15, HU-10): contadores por severidad, mapa simplificado de zonas
 * y lista con los rojos primero. Tocar una zona filtra la lista.
 */
@Composable
fun DashboardScreen(reports: List<Report>, onOpen: (Int) -> Unit, onExit: () -> Unit) {
    var zoneFilter by remember { mutableStateOf<Int?>(null) }
    val visible = reports
        .filter { zoneFilter == null || it.zone == zoneFilter }
        .sortedWith(compareBy({ -it.severity.ordinal }, { it.status.ordinal }))

    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("SUPERVISOR", style = MaterialTheme.typography.headlineLarge, color = ListeningBlue)
                Text("Turno noche · 19:00 a 07:00", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            }
            BigButton("SALIR", onExit, Modifier.height(56.dp).padding(start = 8.dp).size(width = 96.dp, height = 56.dp))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Severity.entries.reversed().forEach { sev ->
                val count = reports.count { it.severity == sev }
                Row(
                    Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(sev.background).padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SeverityShape(sev, fill = sev.content, glyph = sev.background, modifier = Modifier.size(32.dp))
                    Text("$count", style = MaterialTheme.typography.headlineLarge, color = sev.content)
                }
            }
        }

        Text("Mapa de zonas", style = MaterialTheme.typography.titleLarge, color = TextSecondary)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            (1..4).forEach { zone ->
                ZoneTile(zone, reports.filter { it.zone == zone }, selected = zoneFilter == zone, Modifier.weight(1f)) {
                    zoneFilter = if (zoneFilter == zone) null else zone
                }
            }
        }

        Text(
            if (zoneFilter == null) "Reportes (rojos primero)" else "Reportes · zona $zoneFilter",
            style = MaterialTheme.typography.titleLarge,
            color = TextSecondary,
        )
        if (visible.isEmpty()) {
            Text("No hay reportes para este filtro", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        }
        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(visible, key = { it.id }) { ReportRow(it, onClick = { onOpen(it.id) }) }
        }
    }
}

@Composable
private fun ZoneTile(zone: Int, inZone: List<Report>, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    // El color de la zona es su peor reporte abierto; la forma y el número evitan depender solo del color.
    val worst = inZone.filter { it.status.name != "CLOSED" }.maxByOrNull { it.severity.ordinal }?.severity
    val bg = worst?.background ?: Surface2
    val fg = worst?.content ?: TextSecondary
    Column(
        modifier
            .height(88.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Z$zone", style = MaterialTheme.typography.headlineMedium, color = fg)
        Text(if (selected) "FILTRO" else "${inZone.size} rep.", style = MaterialTheme.typography.bodyMedium, color = fg)
    }
}

@Composable
private fun ReportRow(report: Report, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Surface1)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SeverityShape(report.severity, fill = report.severity.background, glyph = report.severity.content, modifier = Modifier.size(48.dp))
        Column(Modifier.weight(1f)) {
            Text(report.type, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
            Text("Zona ${report.zone} · ${report.time} · ${report.status.label}", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun DashboardPreview() = CC451PCL02ProyTheme { DashboardScreen(MockData.supervisorReports, {}, {}) }
