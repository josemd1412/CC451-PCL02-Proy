package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cc451_pcl02_proy.domain.Severity
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.SeverityShape
import com.example.cc451_pcl02_proy.ui.components.SeverityVibration
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.Condensed
import com.example.cc451_pcl02_proy.ui.theme.SignalRed
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val HOLD_MS = 3000
private const val CANCEL_WINDOW_S = 10

/**
 * SOS: se mantiene presionado el círculo 3 s (equivale al botón físico) y se envía sin pedir
 * confirmación. Hay 10 s para cancelar un envío accidental (HU-06).
 */
@Composable
fun SosScreen(onBack: () -> Unit) {
    var sent by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize().background(SignalRed).systemBarsPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ZoneHeader(Modifier.fillMaxWidth(), color = TextPrimary)
        if (sent) SentContent(onCancel = onBack) else HoldContent(onSent = { sent = true }, onBack = onBack)
    }
}

@Composable
private fun ColumnScope.HoldContent(onSent: () -> Unit, onBack: () -> Unit) {
    val progress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var done by remember { mutableStateOf(false) }

    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(280.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onPress = {
                        val job = scope.launch {
                            progress.animateTo(1f, tween(HOLD_MS, easing = LinearEasing))
                            done = true
                            onSent()
                        }
                        tryAwaitRelease()
                        if (!done) {
                            job.cancel()
                            scope.launch { progress.snapTo(0f) }
                        }
                    })
                },
            contentAlignment = Alignment.Center,
        ) {
            Canvas(Modifier.fillMaxSize()) {
                val stroke = 20.dp.toPx()
                drawCircle(TextPrimary.copy(alpha = 0.25f), style = Stroke(stroke), radius = size.minDimension / 2 - stroke / 2)
                drawArc(
                    TextPrimary, -90f, 360f * progress.value, false,
                    topLeft = Offset(stroke / 2, stroke / 2),
                    size = Size(size.width - stroke, size.height - stroke),
                    style = Stroke(stroke, cap = StrokeCap.Round),
                )
            }
            Box(Modifier.size(210.dp).clip(CircleShape).background(TextPrimary), contentAlignment = Alignment.Center) {
                Text("SOS", style = TextStyle(fontFamily = Condensed, fontSize = 72.sp), color = SignalRed)
            }
        }
    }
    Text("MANTÉN PRESIONADO 3 s", style = MaterialTheme.typography.headlineLarge, color = TextPrimary, textAlign = TextAlign.Center)
    Text("O di “SafeMine emergencia”", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
    BigButton("VOLVER", onBack, Modifier.fillMaxWidth(), container = TextPrimary, content = SignalRed)
}

@Composable
private fun ColumnScope.SentContent(onCancel: () -> Unit) {
    SeverityVibration(Severity.DANGER)
    var seconds by remember { mutableIntStateOf(CANCEL_WINDOW_S) }
    LaunchedEffect(Unit) {
        while (seconds > 0) {
            delay(1000)
            seconds--
        }
    }

    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
        SeverityShape(
            Severity.DANGER, fill = TextPrimary, glyph = SignalRed, modifier = Modifier.size(240.dp),
        )
    }
    BasicText(
        "EMERGENCIA ENVIADA",
        maxLines = 1,
        autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, maxFontSize = 64.sp),
        style = TextStyle(color = TextPrimary, fontFamily = Condensed, textAlign = TextAlign.Center),
        modifier = Modifier.fillMaxWidth(),
    )
    Text("La brigada está avisada", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
    if (seconds > 0) {
        BigButton("CANCELAR EMERGENCIA ($seconds s)", onCancel, Modifier.fillMaxWidth().height(96.dp), container = TextPrimary, content = SignalRed)
    } else {
        Text("Quédate en un lugar seguro", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun SosPreview() = CC451PCL02ProyTheme { SosScreen({}) }
