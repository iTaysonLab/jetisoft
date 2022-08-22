package bruhcollective.itaysonlab.microapp.auth.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import bruhcollective.itaysonlab.microapp.auth.R

@Composable
fun AuthDisclaimer(
    navController: NavController
) {
    AlertDialog(
        onDismissRequest = { navController.popBackStack() },
        icon = {
            Icon(Icons.Rounded.Warning, null)
        },
        title = {
            Text(stringResource(id = R.string.auth_disclaimer))
        },
        text = {
            Text(stringResource(id = R.string.auth_disclaimer_text))
        },
        confirmButton = {
            TextButton(onClick = { navController.popBackStack() }) {
                Text(stringResource(id = R.string.auth_disclaimer_confirm))
            }
        })
}