package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.Condensed
import com.example.cc451_pcl02_proy.ui.theme.ListeningBlue
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

/**
 * Estado ESCUCHANDO (pantalla principal del operario). Toda la pantalla es un solo objetivo
 * táctil. En la app real la voz dispara el flujo; aquí un toque lo simula.
 */
@Composable
fun ListeningScreen(onSimulateVoice: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MatteBlack)
            .clickable(role = Role.Button, onClickLabel = "Simular reporte por voz", onClick = onSimulateVoice)
            .systemBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ZoneHeader(Modifier.fillMaxWidth())

        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            PulseRings(Modifier.size(280.dp))
        }

        BasicText(
            "ESCUCHANDO",
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(minFontSize = 32.sp, maxFontSize = 96.sp),
            style = TextStyle(color = ListeningBlue, fontFamily = Condensed, textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            "Di tu reporte",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            "Demo: toca la pantalla para simular tu voz",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
        )
    }
}

/** Círculos concéntricos que laten: indican que la app está oyendo (retroalimentación conductual). */
@Composable
private fun PulseRings(modifier: Modifier = Modifier, color: Color = ListeningBlue) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val phases = (0..2).map { i ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2400, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(i * 800),
            ),
            label = "ring$i",
        )
    }
    Canvas(modifier) {
        val max = size.minDimension / 2
        drawCircle(color, radius = max * 0.28f)
        phases.forEach { phase ->
            val t = phase.value
            drawCircle(
                color = color.copy(alpha = 1f - t),
                radius = max * (0.3f + 0.7f * t),
                style = Stroke(width = 10.dp.toPx()),
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun ListeningPreview() {
    CC451PCL02ProyTheme { ListeningScreen(onSimulateVoice = {}) }
}
