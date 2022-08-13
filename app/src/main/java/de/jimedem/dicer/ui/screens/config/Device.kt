package de.jimedem.dicer.ui.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.data.BaseUrl
import de.jimedem.dicer.ui.screens.elements.DropDownText
import de.jimedem.dicer.ui.screens.elements.RoundedBox

@Composable
fun DeviceDropDown(modifier: Modifier){
    val viewModel = viewModel<DicerViewModel>()
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
       DropDownText(text = viewModel.selectedDevice.collectAsState().value.name, isExpanded = isExpanded) {
           isExpanded = true
       }
        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            DeviceDropDownItem(
                BaseUrl.RaspberryPi,
                changeExpanded = { isExpanded = it })
            DeviceDropDownItem(
                BaseUrl.Laptop,
                changeExpanded = { isExpanded = it })
        }
    }
}

@Composable
fun DeviceDropDownItem(
    device: BaseUrl,
    changeExpanded: (Boolean) -> Unit
) {
    val color = if(device.reachable.collectAsState().value) Color.Green else Color.Red
    val viewModel = viewModel<DicerViewModel>()
    DropdownMenuItem(onClick = {
        viewModel.selectedDevice.value = device
        changeExpanded(false)
    }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(device.name)
            RoundedBox(color = color, Modifier)
        }

    }
}