package ir.sharif.simplenote.ui.features.changepassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ArrowRight
import ir.sharif.simplenote.ui.components.IconPosition
import ir.sharif.simplenote.ui.components.controls.button.Button
import ir.sharif.simplenote.ui.components.controls.button.ButtonSize
import ir.sharif.simplenote.ui.components.controls.button.ButtonStyle
import ir.sharif.simplenote.ui.components.controls.navbar.NavBar
import ir.sharif.simplenote.ui.components.inputs.textinput.TextInputField
import ir.sharif.simplenote.ui.components.overlays.bottommodalnotification.BottomModalNotification
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Preview
@Composable
fun ChangePasswordScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {

    val uiStates by viewModel.uiStates.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = ColorPalette.SuccessBase, // Set background to success green
                    contentColor = ColorPalette.NeutralWhite, // Set text color for contrast,
                    actionColor = ColorPalette.NeutralBaseGrey,
                    dismissActionContentColor = ColorPalette.NeutralLightGrey,
                )
            }
        },
        topBar = {
            NavBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars.
                only(WindowInsetsSides.Top)),
                linkText = "Back",
                onLink = onNavigateBack,
                text = "Change Password",
                borderBottom = true
            )
        }
    ) { innerPadding ->
        Box (
            modifier = Modifier.fillMaxSize()
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Text(
                    text = "Please input your current password first",
                    style = TextStyles.text2XsMedium.copy(
                        color = ColorPalette.PrimaryBase
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                TextInputField(
                    value = uiStates.currentPassword,
                    onValueChange = viewModel::setCurrentPassword,
                    placeholderText = "********",
                    visualTransformation = PasswordVisualTransformation(),
                    label = "Current Password",
                    isError = (uiStates.currentPasswordError != null),
                    caption = uiStates.currentPasswordError
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorPalette.NeutralLightGrey,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Now, create your new password",
                    style = TextStyles.text2XsMedium.copy(
                        color = ColorPalette.PrimaryBase
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                TextInputField(
                    value = uiStates.newPassword,
                    onValueChange = viewModel::setNewPassword,
                    placeholderText = "********",
                    visualTransformation = PasswordVisualTransformation(),
                    label = "New Password",
                    isError = (uiStates.newPasswordError != null),
                    caption = if (uiStates.newPasswordError != null) uiStates.newPasswordError else "Password should contain a-z, A-Z, 0-9"
                )

                TextInputField(
                    value = uiStates.newPasswordRetype,
                    onValueChange = viewModel::setNewPasswordRetype,
                    placeholderText = "********",
                    visualTransformation = PasswordVisualTransformation(),
                    label = "Retype New Password",
                    isError = (uiStates.newPasswordRetypeError != null),
                    caption = uiStates.newPasswordRetypeError
                )
            }

            Button(
                text = "Submit New Password",
                size = ButtonSize.BLOCK,
                style = ButtonStyle.PRIMARY,
                onClick = { viewModel.changePassword() },
                icon = Heroicons.Solid.ArrowRight,
                iconPosition = IconPosition.RIGHT,
                modifier = Modifier.padding(horizontal = 16.dp)
                    .padding(bottom = 54.dp)
                    .align(Alignment.BottomCenter)
            )
        }

    }

    BottomModalNotification(
        notificationTitle = uiStates.notificationTitle,
        notificationContent = uiStates.notificationContent,
        buttonText = "Close",
        onButtonClick = {viewModel.setShowModal(false)},
        onDismissRequest = {viewModel.setShowModal(false)},
        showModal = uiStates.showModal,
    )
}