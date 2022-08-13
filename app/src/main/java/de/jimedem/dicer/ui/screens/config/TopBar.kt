package de.jimedem.dicer.ui.screens.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.ui.theme.FelaColor

@Composable
fun TopBar(){
    val viewModel = viewModel<DicerViewModel>()
    TopAppBar(
        title = {
            Text(
                "Dicer",
                style = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        backgroundColor = FelaColor,
        actions = {
            Icon(imageVector = Icons.Default.Restore, contentDescription = "",
                Modifier
                    .clickable {

                    }
                    .padding(end = 16.dp))
            Icon(imageVector = Icons.Default.Save, contentDescription = "",
                Modifier
                    .clickable {

                    }
                    .padding(end = 8.dp))
        }
    )
}