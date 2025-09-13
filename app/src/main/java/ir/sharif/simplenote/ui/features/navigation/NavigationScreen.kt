package ir.sharif.simplenote.ui.features.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.GifDecoder
import ir.sharif.simplenote.R
import ir.sharif.simplenote.data.local.AuthStatus
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToSync: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.registerWorkers()
    }

    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
                add(GifDecoder.Factory())
            }.build()

    val authStatus by viewModel.authStatus.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.authStatus) {
        delay(3000L)

        when (authStatus) {
            is AuthStatus.Authenticated -> {
                onNavigateToHome()
            }
            is AuthStatus.Syncing -> {
                onNavigateToSync()
            }
            is AuthStatus.Unauthenticated -> {
                onNavigateToOnBoarding()
            }
            else -> {}
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.NeutralWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                model = R.drawable.ic_brand,
                contentDescription = "Simple Note Icon",
                imageLoader = imageLoader,
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.size(32.dp))

            Text("Simple Note App", style = TextStyles.text2XlBold)
        }
    }
}
