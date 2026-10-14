package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.domain.Severity
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.SeverityShape
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.ListeningBlue
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalAmber
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

/**
 * Inicio de turno con el QR del fotocheck, sin teclear (HU-01). Aquí el escaneo se simula con
 * botones; también permite entrar como supervisor para recorrer el flujo de la demo.
 */
@Composable
fun StartShiftScreen(onScanOk: () -> Unit, onSupervisor: () -> Unit) {
    var unregistered by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("SAFEMINE", style = MaterialTheme.typography.headlineLarge, color = ListeningBlue)

        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            if (unregistered) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    SeverityShape(Severity.CAUTION, SignalAmber, MatteBlack, Modifier.size(180.dp))
                    Text("NO REGISTRADO", style = MaterialTheme.typography.headlineLarge, color = SignalAmber)
                    Text("Fotocheck no registrado, avisa a tu supervisor", style = MaterialTheme.typography.bodyLarge, color = TextPrimary, textAlign = TextAlign.Center)
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    ScanFrame(Modifier.size(240.dp))
                    Text("ACERCA TU FOTOCHECK", style = MaterialTheme.typography.headlineLarge, color = TextPrimary, textAlign = TextAlign.Center)
                    Text("Para iniciar tu turno", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            }
        }

        BigButton("SIMULAR QR VÁLIDO", onScanOk, Modifier.fillMaxWidth(), container = ListeningBlue, content = MatteBlack)
        BigButton(
            if (unregistered) "REINTENTAR" else "SIMULAR QR NO REGISTRADO",
            { unregistered = !unregistered },
            Modifier.fillMaxWidth(),
        )
        BigButton("ENTRAR COMO SUPERVISOR", onSupervisor, Modifier.fillMaxWidth())
    }
}

/** Marco de cámara con esquinas, como guía de dónde colocar el QR. */
@Composable
private fun ScanFrame(modifier: Modifier = Modifier) {
    Canvas(modifier.clip(RoundedCornerShape(24.dp))) {
        val stroke = 10.dp.toPx()
        drawRoundRect(
            ListeningBlue,
            topLeft = Offset(stroke / 2, stroke / 2),
            size = Size(size.width - stroke, size.height - stroke),
            cornerRadius = CornerRadius(24.dp.toPx()),
            style = Stroke(stroke),
        )
        drawLine(ListeningBlue, Offset(stroke * 2, size.height / 2), Offset(size.width - stroke * 2, size.height / 2), 4.dp.toPx())
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun StartShiftPreview() = CC451PCL02ProyTheme { StartShiftScreen({}, {}) }
