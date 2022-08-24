package bruhcollective.itaysonlab.jetisoft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import bruhcollective.itaysonlab.jetisoft.ui.screens.AppNavigation
import bruhcollective.itaysonlab.jetisoft.ui.theme.JetisoftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetisoftTheme {
                AppNavigation()
            }
        }
    }
}