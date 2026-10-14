package com.example.cc451_pcl02_proy.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.cc451_pcl02_proy.ui.theme.Surface2
import com.example.cc451_pcl02_proy.ui.theme.TextPrimary

/** Botón para usar con guantes: alto mínimo de 80 dp (~2 cm), sin gestos finos (RNF-05). */
@Composable
fun BigButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    container: Color = Surface2,
    content: Color = TextPrimary,
) {
    Box(
        modifier
            .heightIn(min = 80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(container)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = content)
    }
}
