package bruhcollective.itaysonlab.microapp.smartintel

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import bruhcollective.itaysonlab.microapp.core.Destinations
import bruhcollective.itaysonlab.microapp.smartintel.ui.SmartIntelScreen
import javax.inject.Inject

class SmartIntelMicroappImpl @Inject constructor(): SmartIntelMicroapp() {
    @Composable
    override fun NavGraphBuilder.Content(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        SmartIntelScreen(onYoutubeVideoClick = { id ->
            navController.context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://youtu.be/$id")))
        }, onBackClicked = navController::popBackStack)
    }
}