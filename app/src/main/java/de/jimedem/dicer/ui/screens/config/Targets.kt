package de.jimedem.dicer.ui.screens.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.ui.screens.elements.AddButton
import de.jimedem.dicer.ui.screens.elements.RemoveButton
import de.jimedem.dicer.ui.theme.FelaColor

const val MAX_DICES = 2
const val MAX_RUNS = 4

@Composable
fun TargetList() {
    val viewModel = viewModel<DicerViewModel>()
    val runs = viewModel.runs

    runs.forEachIndexed { index, _ ->
        Targets(run = index)
    }
    AddButton(text = "Durchgang hinzufügen", isEnabled = runs.size < MAX_RUNS) {
        viewModel.addRun()
    }
    RemoveButton(text = "Letzten Durchgang entfernen", isEnabled = runs.size > 1) {
        viewModel.removeLastRun()
    }
}

@Composable
fun Targets(run: Int) {
    val viewModel = viewModel<DicerViewModel>()
    val targets = viewModel.targetList(run) ?: emptyList()
    var showDialog by remember { mutableStateOf(false) }
    var dialogNumber by remember { mutableStateOf(1) }
    var dialogTargetIndex by remember { mutableStateOf(-1) }
    if (showDialog) {
        NumberPickerDialog(dialogNumber, dialogTargetIndex, run) {
            showDialog = false
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Durchgang ${run + 1}:")
        targets.forEachIndexed { index, target ->
            Card(backgroundColor = FelaColor, modifier = Modifier.size(24.dp)) {
                Text(
                    text = target.toString(),
                    modifier = Modifier
                        .clickable {
                            dialogNumber = target
                            dialogTargetIndex = index
                            showDialog = true
                        }
                        .align(Alignment.CenterVertically),
                    style = LocalTextStyle.current.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        AddButton("Zahl hinzufügen", targets.size < MAX_DICES) {
            dialogTargetIndex = -1
            dialogNumber = 1
            showDialog = true
        }
    }
}