package com.example.cc451_pcl02_proy.ui.components

import androidx.compose.ui.graphics.Color
import com.example.cc451_pcl02_proy.domain.Severity
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalAmber
import com.example.cc451_pcl02_proy.ui.theme.SignalGreen
import com.example.cc451_pcl02_proy.ui.theme.SignalRed
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary

/** Color de fondo del semáforo a pantalla completa. */
val Severity.background: Color
    get() = when (this) {
        Severity.OK -> SignalGreen
        Severity.CAUTION -> SignalAmber
        Severity.DANGER -> SignalRed
    }

/** Color de texto y forma sobre [background]; todos cumplen contraste >= 7:1. */
val Severity.content: Color
    get() = when (this) {
        Severity.OK, Severity.CAUTION -> MatteBlack
        Severity.DANGER -> TextPrimary
    }
