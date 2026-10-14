package com.example.cc451_pcl02_proy.ui.components

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.cc451_pcl02_proy.domain.Severity
import kotlinx.coroutines.delay

private fun Context.vibrator(): Vibrator? = getSystemService(Vibrator::class.java)

/**
 * Vibra con el patrón de [severity]: 1 pulso (OK), 2 pulsos (PRECAUCIÓN), continuo (PELIGRO).
 * Se cancela al salir de la pantalla (RF-20, RNF-04).
 */
@Composable
fun SeverityVibration(severity: Severity) {
    val context = LocalContext.current
    DisposableEffect(severity) {
        val vibrator = context.vibrator()
        if (vibrator?.hasVibrator() == true) {
            val repeat = if (severity.repeats) 0 else -1
            vibrator.vibrate(VibrationEffect.createWaveform(severity.vibration, repeat))
        }
        onDispose { vibrator?.cancel() }
    }
}

/**
 * Destello de pantalla sincronizado con la vibración, para quien no la siente ni oye el audio.
 * Devuelve la opacidad del destello (0..1) que la pantalla dibuja sobre el semáforo.
 */
@Composable
fun rememberSeverityFlash(severity: Severity): Animatable<Float, *> {
    val alpha = remember(severity) { Animatable(0f) }
    LaunchedEffect(severity) {
        val pattern = severity.vibration
        do {
            var i = 0
            while (i < pattern.size) {
                delay(pattern[i])
                if (i + 1 < pattern.size) {
                    alpha.snapTo(0.65f)
                    alpha.animateTo(0f, tween(pattern[i + 1].toInt()))
                }
                i += 2
            }
        } while (severity.repeats)
    }
    return alpha
}
