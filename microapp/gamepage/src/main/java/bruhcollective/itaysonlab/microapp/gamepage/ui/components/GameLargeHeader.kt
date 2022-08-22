package bruhcollective.itaysonlab.microapp.gamepage.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.TipsAndUpdates
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.microapp.core.util.DateUtils
import bruhcollective.itaysonlab.microapp.gamepage.R
import coil.compose.AsyncImage

@Composable
fun GameLargeHeader(
    bgUrl: String?,
    logoUrl: String?,
    name: String,
    lastPlayed: String,
    platform: String,
    fbUrl: String?,
    instUrl: String?,
    websiteUrl: String?,
    redditUrl: String?,
    smartIntelSupported: Boolean
) {
    val surfaceColor = MaterialTheme.colorScheme.surface

    val subtitle = remember(lastPlayed, platform) {
        "last played ${DateUtils.formatRelative(lastPlayed)} via $platform"
    }

    val context = LocalContext.current
    val openInBrowser = remember { { url: String -> context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))) } }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        AsyncImage(
            model = bgUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Transparent,
                                //0.5f to surfaceColor.copy(alpha = 0.5f),
                                //0.85f to surfaceColor,
                                1f to surfaceColor,
                            )
                        )
                    )
                },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (logoUrl != null) {
                AsyncImage(
                    model = logoUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(fraction = 0.5f).heightIn(max = 150.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }

            Text(text = subtitle)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (!fbUrl.isNullOrEmpty()) {
                    IconButton(onClick = { openInBrowser(fbUrl) }) {
                        Icon(painterResource(id = R.drawable.logo_facebook_24), contentDescription = null)
                    }
                }

                if (!instUrl.isNullOrEmpty()) {
                    IconButton(onClick = { openInBrowser(instUrl) }) {
                        Icon(painterResource(id = R.drawable.logo_instagram_24), contentDescription = null)
                    }
                }

                if (smartIntelSupported) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Rounded.TipsAndUpdates, contentDescription = null)
                    }
                }

                if (!websiteUrl.isNullOrEmpty()) {
                    IconButton(onClick = { openInBrowser(websiteUrl) }) {
                        Icon(Icons.Rounded.Language, contentDescription = null)
                    }
                }
            }
        }
    }
}