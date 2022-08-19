package de.jimedem.dicer.ui.screens.config

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.data.Animation
import de.jimedem.dicer.ui.screens.elements.DropDownText

@Composable
fun AnimationDropDown() {
    val viewModel = viewModel<DicerViewModel>()
    var expanded by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth()) {
        Text("Animation:")
        Box {
            DropDownText(
                text = viewModel.animation.collectAsState().value.displayText,
                isExpanded = expanded,
                Modifier.fillMaxWidth()
            ) {
                expanded = true
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                AnimationDropDownItem(
                    animation = Animation.None,
                    changeExpanded = { expanded = it })
                AnimationDropDownItem(
                    animation = Animation.Animation1,
                    changeExpanded = { expanded = it })
                AnimationDropDownItem(
                    animation = Animation.Animation2,
                    changeExpanded = { expanded = it })
                AnimationDropDownItem(
                    animation = Animation.CubeAnimation,
                    changeExpanded = { expanded = it })
            }
        }
    }
}

@Composable
fun AnimationDropDownItem(
    animation: Animation,
    changeExpanded: (Boolean) -> Unit
) {
    val viewModel = viewModel<DicerViewModel>()
    DropdownMenuItem(onClick = {
        viewModel.animation.value = animation
        changeExpanded(false)
    }) {
        Text(animation.displayText)
    }
}