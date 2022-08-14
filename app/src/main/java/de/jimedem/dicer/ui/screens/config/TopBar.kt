package de.jimedem.dicer.ui.screens.config

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.ui.theme.FelaColor

@Preview
@Composable
fun TopBar() {
    val viewModel = viewModel<DicerViewModel>()
    val context = LocalContext.current
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
            Icon(
                imageVector = Icons.Default.Restore, contentDescription = "", modifier =
                Modifier
                    .clickable {
                        viewModel.restoreDefaultConfiguration(context) {
                            Toast
                                .makeText(
                                    context,
                                    "Standardkonfiguration wiederhergestellt",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    },
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Save, contentDescription = "",
                Modifier
                    .clickable {
                        viewModel.saveConfiguration(context) {
                            Toast
                                .makeText(context, "Konfiguration gespeichert", Toast.LENGTH_LONG)
                                .show()
                        }
                    },
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    )
}