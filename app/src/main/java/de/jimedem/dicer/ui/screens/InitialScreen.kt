package de.jimedem.dicer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.jimedem.dicer.DicerViewModel

//@Composable
//@Preview(showSystemUi = true)
//fun ConnectionIndicator() {
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        val viewModel = viewModel<DicerViewModel>()
//        val text by viewModel.baseUrl.collectAsState()
//        TextField(value = text, onValueChange = {viewModel.baseUrl.value = it})
//        TextButton(onClick = {viewModel.checkConnection()}) {
//            Text(text = "Verbinden")
//        }
//    }
//}