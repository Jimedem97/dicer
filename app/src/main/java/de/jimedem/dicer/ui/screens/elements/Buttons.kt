package de.jimedem.dicer.ui.screens.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import de.jimedem.dicer.ui.theme.FelaColor
import de.jimedem.dicer.ui.theme.FelaColorDisabled

@Composable
fun AddButton(text: String, isEnabled: Boolean, onClick: () -> Unit) {
    DicerIconButton(icon = Icons.Default.Add, text = text, isEnabled = isEnabled, onClick = onClick)
}

@Composable
fun RemoveButton(text: String, isEnabled: Boolean, onClick: () -> Unit) {
    DicerIconButton(icon = Icons.Default.Delete, text = text, isEnabled = isEnabled, onClick = onClick)
}

@Composable
fun SendButton(text: String, modifier: Modifier, onClick: () -> Unit){
    Card(backgroundColor = FelaColor) {
        IconButton(onClick = onClick, enabled = true, modifier = modifier) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text, style = LocalTextStyle.current.copy(color = MaterialTheme.colors.onPrimary))
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun DicerIconButton(icon: ImageVector, text: String, isEnabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(backgroundColor = if (isEnabled) FelaColor else FelaColorDisabled.compositeOver(Color.White)) {
        IconButton(onClick = onClick, enabled = isEnabled, modifier = modifier) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onPrimary
                )
                Text(text, style = LocalTextStyle.current.copy(color = MaterialTheme.colors.onPrimary))
            }
        }
    }
}