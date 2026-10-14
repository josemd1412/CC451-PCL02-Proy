package com.example.cc451_pcl02_proy.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.cc451_pcl02_proy.domain.Severity
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Forma propia de cada severidad: círculo (OK), triángulo (PRECAUCIÓN) y octágono (PELIGRO).
 * [fill] pinta la forma y [glyph] el símbolo interior.
 */
@Composable
fun SeverityShape(severity: Severity, fill: Color, glyph: Color, modifier: Modifier = Modifier) {
    Canvas(modifier.aspectRatio(1f)) {
        val w = size.minDimension
        fun p(x: Float, y: Float) = Offset(x * w, y * w)
        val round = Stroke(width = 0.05f * w, cap = StrokeCap.Round, join = StrokeJoin.Round)

        when (severity) {
            Severity.OK -> {
                drawCircle(fill)
                drawLine(glyph, p(0.28f, 0.52f), p(0.44f, 0.68f), 0.09f * w, StrokeCap.Round)
                drawLine(glyph, p(0.44f, 0.68f), p(0.74f, 0.34f), 0.09f * w, StrokeCap.Round)
            }
            Severity.CAUTION -> {
                val path = Path().apply {
                    moveTo(0.5f * w, 0.08f * w); lineTo(0.95f * w, 0.9f * w)
                    lineTo(0.05f * w, 0.9f * w); close()
                }
                drawPath(path, fill)
                drawPath(path, fill, style = round)
                exclamation(glyph, w, top = 0.38f, bottom = 0.64f, dot = 0.78f)
            }
            Severity.DANGER -> {
                val r = 0.5f / cos(PI / 8).toFloat()
                val path = Path()
                for (k in 0 until 8) {
                    val a = PI / 8 + k * PI / 4
                    val pt = p(0.5f + r * cos(a).toFloat(), 0.5f + r * sin(a).toFloat())
                    if (k == 0) path.moveTo(pt.x, pt.y) else path.lineTo(pt.x, pt.y)
                }
                path.close()
                drawPath(path, fill)
                exclamation(glyph, w, top = 0.24f, bottom = 0.58f, dot = 0.75f)
            }
        }
    }
}

private fun DrawScope.exclamation(color: Color, w: Float, top: Float, bottom: Float, dot: Float) {
    drawLine(color, Offset(0.5f * w, top * w), Offset(0.5f * w, bottom * w), 0.09f * w, StrokeCap.Round)
    drawCircle(color, 0.05f * w, Offset(0.5f * w, dot * w))
}
