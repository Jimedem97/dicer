package de.jimedem.dicer.ui.screens.config

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.ui.screens.elements.DropDownText
import de.jimedem.dicer.ui.theme.FelaColor

@Preview
@Composable
fun PreviewNumberPickerDialog() {
    NumberPickerDialog(run = 1, targetIndex = 1) {

    }
}

@Composable
fun NumberPickerDialog(
    initialNumber: Int = 1,
    targetIndex: Int = -1,
    run: Int,
    onDismissDialog: () -> Unit
) {
    val viewModel = viewModel<DicerViewModel>()
    var number by remember { mutableStateOf(initialNumber) }

    Dialog(onDismissRequest = onDismissDialog, properties = DialogProperties()) {
        Card(elevation = 0.dp, shape = RoundedCornerShape(8.dp)) {
            Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Zahl auswählen:")
                NumberDropDown(number) { number = it }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (targetIndex > -1) {
                        TextButton(
                            onClick = {
                                viewModel.removeTarget(run, targetIndex)
                                onDismissDialog()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = FelaColor,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Löschen")
                        }
                    }
                    TextButton(
                        onClick = {
                            if (targetIndex > -1) {
                                viewModel.modifyTarget(run, targetIndex, number)
                            } else {
                                viewModel.addTarget(run, number)
                            }
                            onDismissDialog()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = FelaColor,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Speichern")
                    }
                }

            }
        }

    }
}

@Composable
fun NumberDropDown(number: Int, onNumberSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        DropDownText(text = "$number", isExpanded = expanded, modifier = Modifier.fillMaxWidth()) {
            expanded = true
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            (1..6).toList().forEach { dropDownNumber ->
                DropdownMenuItem(onClick = {
                    onNumberSelected(dropDownNumber)
                    expanded = false
                }) {
                    Text("$dropDownNumber")
                }
            }
        }
    }


}