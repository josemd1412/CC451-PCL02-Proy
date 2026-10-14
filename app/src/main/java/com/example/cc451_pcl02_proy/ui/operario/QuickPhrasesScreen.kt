package com.example.cc451_pcl02_proy.ui.operario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.data.MockData
import com.example.cc451_pcl02_proy.ui.components.BigButton
import com.example.cc451_pcl02_proy.ui.components.ZoneHeader
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme
import com.example.cc451_pcl02_proy.ui.theme.ListeningBlue
import com.example.cc451_pcl02_proy.ui.theme.MatteBlack
import com.example.cc451_pcl02_proy.ui.theme.Surface1
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary
import com.example.cc451_pcl02_proy.ui.theme.TextSecondary

/**
 * 8 frases rápidas para quien tiene dificultad del habla (HU-16): se eligen con un botón
 * grande o diciendo su número. [onPick] recibe el índice de la frase.
 */
@Composable
fun QuickPhrasesScreen(onPick: (Int) -> Unit, onBack: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(MatteBlack).systemBarsPadding().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ZoneHeader(Modifier.fillMaxWidth())
        Text("Elige o di el número", style = MaterialTheme.typography.headlineMedium, color = TextSecondary)

        MockData.quickPhrases.chunked(2).forEachIndexed { row, pair ->
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                pair.forEachIndexed { col, phrase ->
                    val number = row * 2 + col + 1
                    PhraseButton(number, phrase.label, { onPick(number - 1) }, Modifier.weight(1f))
                }
            }
        }
        BigButton("VOLVER", onBack, Modifier.fillMaxWidth())
    }
}

@Composable
private fun PhraseButton(number: Int, label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .heightIn(min = 80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Surface1)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column {
            Text("$number", style = MaterialTheme.typography.headlineLarge, color = ListeningBlue)
            Text(label.uppercase(), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        }
    }
}

@Preview(widthDp = 360, heightDp = 740)
@Composable
private fun QuickPhrasesPreview() = CC451PCL02ProyTheme { QuickPhrasesScreen({}, {}) }
