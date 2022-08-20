package bruhcollective.itaysonlab.jetisoft.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/*private val DarkColorScheme = darkColorScheme(
    primary = UbiPrimaryOne,
    secondaryContainer = UbiPrimaryOne,
    onSecondaryContainer = UbiTextOne,
    surface = UbiSurface,
    background = UbiSurface,
    onSurface = UbiTextOne,
    surfaceVariant = UbiSurfaceVariant,
    onPrimary = UbiTextOne,
    outline = UbiSurfaceVariantHighest,
    surfaceTint = Color(0xFF4A4A4A),
    outlineVariant = UbiPrimaryOne,
    error = UbiNegativeOne
)*/

private val UbiShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp)
)

@Composable
fun JetisoftTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> darkColorScheme()
    }

    val systemUiController = rememberSystemUiController()
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = !darkTheme
            )
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = UbiShapes,
        content = content
    )
}