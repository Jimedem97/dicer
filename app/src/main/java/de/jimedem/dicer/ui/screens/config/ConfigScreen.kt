package de.jimedem.dicer.ui.screens.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.ui.screens.elements.RoundedBox
import de.jimedem.dicer.ui.screens.elements.SendButton
import de.jimedem.dicer.ui.theme.FelaColor

@Composable
@Preview
fun ConfigScreen() {
    val viewModel = viewModel<DicerViewModel>()
    val selectedDevice = viewModel.selectedDevice.collectAsState().value
    val color = if (selectedDevice.reachable.collectAsState().value) Color.Green else Color.Red
    Scaffold(topBar = { TopBar() }) {
        MaterialTheme.colors.onBackground
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)

        ) {
            Column {
                Text("Abspielgerät:")
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    DeviceDropDown(Modifier)
                    Spacer(modifier = Modifier.weight(1f))
                    RoundedBox(color = color, Modifier)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            TextConfigElement(
                heading = "Initiale Tick Zeit in Ms:",
                initialNumberText = 100,
                onTextChanged = {})
            TextConfigElement(
                heading = "Tickerhöhung pro Tick in %:",
                initialNumberText = 10,
                onTextChanged = {})
            TextConfigElement(
                heading = "Letzter Tick wenn Tickzeit bei (in Ms):",
                initialNumberText = 1000,
                onTextChanged = {})
            TargetList()
            AnimationDropDown()
            SendButton(text = "Senden", modifier = Modifier.fillMaxWidth()) {
                viewModel.sendConfiguration()
            }
        }
    }

}

@Composable
fun TextConfigElement(heading: String, initialNumberText: Int, onTextChanged: (String) -> Unit) {
    var text by remember {
        mutableStateOf(initialNumberText.toString())
    }
    Column(Modifier.fillMaxWidth()) {
        Text(text = heading)
        TextField(
            value = text,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            onValueChange = { text = it; onTextChanged(it) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = FelaColor,
                focusedIndicatorColor = FelaColor
            )
        )
    }
}


