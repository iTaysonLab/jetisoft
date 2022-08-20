package bruhcollective.itaysonlab.jetisoft.ui.kit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import bruhcollective.itaysonlab.jetisoft.ui.screens.LocalNavigationController

@Composable
fun BackButton() {
    val nc = LocalNavigationController.current
    IconButton(onClick = nc::popBackStack) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
    }
}