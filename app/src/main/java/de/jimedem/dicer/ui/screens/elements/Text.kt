package de.jimedem.dicer.ui.screens.elements

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatAnimationSpec
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import de.jimedem.dicer.ui.theme.FelaColor

@Composable
fun DropDownText(
    text: String,
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember { Animatable(currentRotation) }
    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            rotation.animateTo(180f, animationSpec = FloatTweenSpec(200)) {
                currentRotation = value
            }
        } else {
            rotation.animateTo(0f, animationSpec = FloatTweenSpec(200)) { currentRotation = value }
        }

    }
    OutlinedTextField(
        value = text,
        onValueChange = {},
        readOnly = true,
        enabled = false,
        modifier = modifier.then(Modifier.clickable { onClick() }),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "",
                Modifier.rotate(currentRotation)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = FelaColor,
            disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
            backgroundColor = Color.White,
            disabledTrailingIconColor = Color.Black
        )
    )
}