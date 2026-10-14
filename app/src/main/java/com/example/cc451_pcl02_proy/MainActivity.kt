package com.example.cc451_pcl02_proy

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.domain.Severity
import com.example.cc451_pcl02_proy.ui.components.SeverityShape
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalAmber
import com.example.cc451_pcl02_proy.ui.theme.SignalGreen
import com.example.cc451_pcl02_proy.ui.theme.SignalRed
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        setContent {
            CC451PCL02ProyTheme {
                ShapesCatalog()
            }
        }
    }
}

/** Catálogo temporal del sistema de diseño; se reemplaza por la navegación de pantallas. */
@Composable
private fun ShapesCatalog() {
    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SeverityShape(Severity.OK, SignalGreen, MatteBlack, Modifier.size(120.dp))
        SeverityShape(Severity.CAUTION, SignalAmber, MatteBlack, Modifier.size(120.dp))
        SeverityShape(Severity.DANGER, SignalRed, TextPrimary, Modifier.size(120.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ShapesCatalogPreview() {
    CC451PCL02ProyTheme { ShapesCatalog() }
}
