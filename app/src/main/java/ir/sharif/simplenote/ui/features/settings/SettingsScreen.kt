package ir.sharif.simplenote.ui.features.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import ir.sharif.simplenote.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ArrowRightOnRectangle
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Envelope
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.LockClosed
import ir.sharif.simplenote.ui.components.controls.navbar.NavBar
import ir.sharif.simplenote.ui.components.overlays.dialog.SimpleDialog
import ir.sharif.simplenote.ui.components.menu.extramenusingle.ExtraMenuItem
import ir.sharif.simplenote.ui.components.menu.extramenusingle.ExtraMenuItemStyle
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateChangePassword: () -> Unit = {},
    onLogoutNavigation: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStates.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event){
                is SettingsViewModel.NavigationEvent.ToLogin ->
                    onLogoutNavigation()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    Scaffold(
        topBar = {
            NavBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars.
                only(WindowInsetsSides.Top)),
                "Back",
                onNavigateBack,
                text = "Settings",
                borderBottom = true
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = "Profile photo",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Column (
                        modifier = Modifier.height(64.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        val firstName = uiState.firstName
                        val lastName = uiState.lastName
                        if (firstName != null && lastName != null) {
                            Text(
                                text = "$firstName $lastName",
                                style = TextStyles.textLgBold.copy(
                                    color = ColorPalette.NeutralBlack
                                )
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Heroicons.Outline.Envelope,
                                "Envelop",
                                tint = ColorPalette.NeutralDarkGrey,
                                modifier = Modifier.size(15.dp)
                            )

                            Text(
                                text = if (uiState.email != null) uiState.email!! else "No email registered",
                                style = TextStyles.text2Xs.copy(
                                    color = ColorPalette.NeutralDarkGrey
                                )
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = ColorPalette.NeutralLightGrey
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "APP SETTINGS",
                        style = TextStyles.textXs.copy(
                            color = ColorPalette.NeutralDarkGrey
                        ),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    ExtraMenuItem(
                        text = "Change Password",
                        onClick = onNavigateChangePassword,
                        leftIcon = Heroicons.Outline.LockClosed,
                        style = ExtraMenuItemStyle.Arrow
                    )

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = ColorPalette.NeutralLightGrey
                    )

                    ExtraMenuItem(
                        text = "Log Out",
                        onClick = { viewModel.setIsLogOutDialogVisible(true) },
                        leftIcon = Heroicons.Outline.ArrowRightOnRectangle,
                        style = ExtraMenuItemStyle.Danger
                    )
                }


            }
        }
    }

    if (uiState.isLogOutDialogVisible) {
        SimpleDialog(
            "Log Out",
            "Are you sure you want to log out from the application?",
            onDismissRequest = {viewModel.setIsLogOutDialogVisible(false)},
            dismissText = "Cancel",
            onConfirmation = viewModel::logout,
            confirmText = "Yes"
        )
    }
}
