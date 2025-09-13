package ir.sharif.simplenote.ui.features.sync

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ArrowRight

import ir.sharif.simplenote.R
import ir.sharif.simplenote.ui.components.IconPosition
import ir.sharif.simplenote.ui.components.controls.button.Button
import ir.sharif.simplenote.ui.components.controls.button.ButtonSize
import ir.sharif.simplenote.ui.components.controls.button.ButtonStyle
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles
import java.util.logging.Logger

@Composable
fun SyncScreen(
    viewModel: SyncViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(uiState.status) {
        when (uiState.status) {
            SyncStatus.FINISHED -> {
                onNavigateToHome()
            }
            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.startSync()
    }

    Scaffold(
        containerColor = ColorPalette.PrimaryBase,
        contentColor = ColorPalette.NeutralWhite,
    ){ innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)
            .padding(top=128.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            when(uiState.status) {
                SyncStatus.IN_PROGRESS, SyncStatus.FINISHED -> {
                    Image(
                        painterResource(R.drawable.ready_illustration),
                        contentDescription = "getting things ready illustration",
                        modifier = Modifier.size(280.dp)
                    )
                    Text("Getting things ready", style = TextStyles.textXl)
                }
                SyncStatus.FAILED -> {
                    Image(
                        painterResource(R.drawable.ready_illustration),
                        contentDescription = "sync failed illustration",
                        modifier = Modifier.size(280.dp)
                    )
                    Text("Couldn't retrieve your old notes we will try again. In the meanwhile" +
                            " try the app in offline mode", style = TextStyles.textXl)

                    Box(modifier = Modifier.fillMaxSize()) {
                        Button(
                            "Take Me To Home",
                            ButtonStyle.SECONDARY,
                            ButtonSize.BLOCK,
                            onClick = viewModel::onAcknowledgeSyncFailureClicked,
                            icon = Heroicons.Solid.ArrowRight,
                            iconPosition = IconPosition.RIGHT,
                        )
                    }
                }

            }
        }
    }
}
