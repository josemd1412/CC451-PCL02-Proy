package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.ListeningBlue
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.SignalGreen
import com.example.cc451_pcl02_proy.ui.theme.Surface1
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

private val vocabulary = listOf("desatado", "shotcrete", "cachorro", "labor 3")

/**
 * Perfil persistente (RF-11): área, turno, vocabulario y preferencias de salida.
 * Los valores son simulados; la persistencia real llega con el backend.
 */
@Composable
fun ProfileScreen(onCrew: () -> Unit, onBack: () -> Unit) {
    var listenSeconds by remember { mutableIntStateOf(6) }
    var haptic by remember { mutableStateOf(true) }
    var slowTts by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ZoneHeader(Modifier.fillMaxWidth())

        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Card {
                Label("Operario")
                Value("Carlos Quispe")
                Label("Área y zona habitual")
                Value("Mina subterránea · nivel 3200 · zona 3")
                Label("Turno")
                Value("Noche, 19:00 a 07:00")
            }
            Card {
                Label("Vocabulario de mi área")
                Text(vocabulary.joinToString("  ·  "), style = MaterialTheme.typography.headlineMedium, color = ListeningBlue)
            }
            Card {
                Label("Tiempo de escucha")
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    BigButton("−", { if (listenSeconds > 3) listenSeconds-- }, Modifier.size(80.dp))
                    Text("$listenSeconds s", style = MaterialTheme.typography.headlineLarge, color = TextPrimary, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    BigButton("+", { if (listenSeconds < 10) listenSeconds++ }, Modifier.size(80.dp))
                }
            }
            Card {
                ToggleRow("Vibración y destello", haptic) { haptic = it }
                ToggleRow("Voz lenta", slowTts) { slowTts = it }
            }
            BigButton("MI CUADRILLA", onCrew, Modifier.fillMaxWidth(), container = SignalGreen, content = MatteBlack)
        }
        BigButton("VOLVER", onBack, Modifier.fillMaxWidth())
    }
}

@Composable
private fun Card(content: @Composable () -> Unit) {
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Surface1).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) { content() }
}

@Composable
private fun Label(text: String) = Text(text, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)

@Composable
private fun Value(text: String) = Text(text, style = MaterialTheme.typography.titleLarge, color = TextPrimary)

@Composable
private fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().height(72.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.titleLarge, color = TextPrimary, modifier = Modifier.weight(1f))
        Switch(
            checked, onChange,
            colors = SwitchDefaults.colors(checkedTrackColor = SignalGreen, checkedThumbColor = MatteBlack),
        )
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun ProfilePreview() = CC451PCL02ProyTheme { ProfileScreen({}, {}) }
