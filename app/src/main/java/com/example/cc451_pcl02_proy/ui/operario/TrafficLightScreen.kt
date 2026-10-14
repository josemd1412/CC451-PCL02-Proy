package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cc451_pcl02_proy.data.MockData
import com.example.cc451_pcl02_proy.domain.Report
import com.example.cc451_pcl02_proy.domain.Severity
import com.example.cc451_pcl02_proy.ui.components.SeverityShape
import com.example.cc451_pcl02_proy.ui.components.SeverityVibration
import com.example.cc451_pcl02_proy.ui.components.rememberSeverityFlash
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.components.background
import com.example.cc451_pcl02_proy.ui.components.content
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.Condensed

/**
 * Semáforo a pantalla completa: un solo estado visible, con color, forma y palabra (RF-07).
 * El protocolo se muestra también en texto para quien no oye el audio (HU-14 Esc. 2).
 */
@Composable
fun TrafficLightScreen(report: Report, onDismiss: () -> Unit) {
    val severity = report.severity
    val fg = severity.content
    // En PELIGRO la forma es blanca y el símbolo rojo; en los otros, forma negra y símbolo del color de fondo.
    val glyph = severity.background

    SeverityVibration(severity)
    val flash = rememberSeverityFlash(severity)

    Box(
        Modifier
            .fillMaxSize()
            .background(severity.background)
            .clickable(role = Role.Button, onClickLabel = "Volver a escuchar", onClick = onDismiss),
    ) {
        Column(
            Modifier.fillMaxSize().systemBarsPadding().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ZoneHeader(Modifier.fillMaxWidth(), color = fg)

            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                SeverityShape(severity, fill = fg, glyph = glyph, modifier = Modifier.fillMaxHeight().padding(8.dp))
            }

            BasicText(
                severity.word,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 32.sp, maxFontSize = 96.sp),
                style = TextStyle(color = fg, fontFamily = Condensed, textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
            )
            Text(severity.hint, style = MaterialTheme.typography.headlineMedium, color = fg)

            if (report.protocol.isNotEmpty()) ProtocolCard(report.protocol, fg)
        }
        // Destello sobre todo el contenido; no consume toques.
        Box(Modifier.fillMaxSize().graphicsLayer { alpha = flash.value }.background(Color.White))
    }
}

@Composable
private fun ProtocolCard(steps: List<String>, textColor: Color) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(textColor.copy(alpha = 0.14f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        steps.take(3).forEachIndexed { i, step ->
            Text("${i + 1}. $step", style = MaterialTheme.typography.bodyLarge, color = textColor)
        }
    }
}

@Preview(name = "OK", widthDp = 360, heightDp = 740)
@Composable
private fun OkPreview() = CC451PCL02ProyTheme { TrafficLightScreen(MockData.voiceDemos[1], {}) }

@Preview(name = "Precaución", widthDp = 360, heightDp = 740)
@Composable
private fun CautionPreview() = CC451PCL02ProyTheme { TrafficLightScreen(MockData.voiceDemos[0], {}) }

@Preview(name = "Peligro", widthDp = 360, heightDp = 740)
@Composable
private fun DangerPreview() = CC451PCL02ProyTheme { TrafficLightScreen(MockData.voiceDemos[2], {}) }
