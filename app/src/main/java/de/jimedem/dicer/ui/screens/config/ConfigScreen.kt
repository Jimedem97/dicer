package de.jimedem.dicer.ui.screens.config

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel
import de.jimedem.dicer.common.toSanitizedInt
import de.jimedem.dicer.ui.screens.elements.RoundedBox
import de.jimedem.dicer.ui.screens.elements.SendButton
import de.jimedem.dicer.ui.theme.FelaColor

@Composable
@Preview
fun ConfigScreen() {
    val viewModel = viewModel<DicerViewModel>()
    val selectedDevice = viewModel.selectedDevice.collectAsState().value
    val color = if (selectedDevice.reachable.collectAsState().value) Color.Green else Color.Red
    val context = LocalContext.current
    Scaffold(topBar = { TopBar() }) {
        MaterialTheme.colors.onBackground
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                Text("Abspielgerät:")
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    DeviceDropDown(Modifier)
                    Spacer(modifier = Modifier.weight(1f))
                    RoundedBox(color = color)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            TextConfigElement(
                heading = "Initiale Tick Zeit in Ms:",
                initialNumberText = viewModel.initialTickDurationMs.collectAsState().value,
                onTextChanged = {text -> viewModel.initialTickDurationMs.value = text})
            TextConfigElement(
                heading = "Tickerhöhung pro Tick in %:",
                initialNumberText = viewModel.percentTickIncrease.collectAsState().value,
                onTextChanged = {text -> viewModel.percentTickIncrease.value = text})
            TextConfigElement(
                heading = "Letzter Tick wenn Tickzeit bei (in Ms):",
                initialNumberText = viewModel.lastTickMs.collectAsState().value,
                onTextChanged = {text -> viewModel.lastTickMs.value = text})
            TargetList()
            AnimationDropDown()
            SendButton(text = "Konfiguration senden", modifier = Modifier.fillMaxWidth()) {
                viewModel.sendConfiguration { success ->
                    val text = if(success){
                        "Konfiguration erfolgreich gesendet"
                    } else{
                        "Konfiguration konnte nicht gesendet werden"
                    }
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                }
            }
            SendButton(text = "Starten", modifier = Modifier.fillMaxWidth()) {
                viewModel.sendStart {success ->
                    val text = if(success){
                        "Dicer erfolgreich gestartet"
                    } else{
                        "Dicer konnte nicht gestartet werden"
                    }
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                }
            }
            SendButton(text = "Stoppen", modifier = Modifier.fillMaxWidth()) {
                viewModel.sendStop {success ->
                    val text = if(success){
                        "Dicer erfolgreich gestoppt"
                    } else{
                        "Dicer konnte nicht gestoppt werden"
                    }
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
fun TextConfigElement(heading: String, initialNumberText: String, onTextChanged: (String) -> Unit) {
    println("textConfigElement: $initialNumberText")
    Column(Modifier.fillMaxWidth()) {
        Text(text = heading)
        TextField(
            value = initialNumberText,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            onValueChange = { onTextChanged(it) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = FelaColor,
                focusedIndicatorColor = FelaColor
            )
        )
    }
}


