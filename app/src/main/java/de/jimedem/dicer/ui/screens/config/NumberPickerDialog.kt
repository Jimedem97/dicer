package de.jimedem.dicer.ui.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel

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
        Column(Modifier.background(Color.White)) {
            Text(text = "Zahl auswählen:")
            NumberDropDown(number) { number = it }
            Row {
                if (targetIndex > -1) {
                    TextButton(onClick = {
                        viewModel.removeTarget(run, targetIndex)
                        onDismissDialog()
                    }) {
                        Text(text = "Nummer entfernen")
                    }
                }
                TextButton(onClick = {
                    if (targetIndex > -1) {
                        viewModel.modifyTarget(run, targetIndex, number)
                    } else {
                        viewModel.addTarget(run, number)
                    }
                    onDismissDialog()
                }) {
                    Text(text = "Speichern")
                }
            }

        }
    }
}

@Composable
fun NumberDropDown(number: Int, onNumberSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Text("$number", Modifier.clickable { expanded = true })
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