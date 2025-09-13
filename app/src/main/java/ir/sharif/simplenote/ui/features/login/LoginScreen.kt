package ir.sharif.simplenote.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ArrowRight
import ir.sharif.simplenote.ui.components.IconPosition
import ir.sharif.simplenote.ui.components.controls.button.Button
import ir.sharif.simplenote.ui.components.controls.button.ButtonSize
import ir.sharif.simplenote.ui.components.controls.button.ButtonStyle
import ir.sharif.simplenote.ui.components.controls.dividerwithtext.DividerWithText
import ir.sharif.simplenote.ui.components.inputs.textinput.TextInputField
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles


@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit,
                onNavigateToSync: () -> Unit,
                viewModel: LoginViewModel = hiltViewModel()) {

    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.navigationDestination) {
        when (state.navigationDestination) {
            NavigationDestination.REGISTER -> {
                onNavigateToRegister()
                viewModel.consumeNavigationDestination()
            }
            NavigationDestination.SYNC -> {
                onNavigateToSync()
                viewModel.consumeNavigationDestination()
            }
            else -> {}
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ColorPalette.NeutralWhite
    ) {
        Row(
            modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Let's Login",
                        style = TextStyles.text2XlBold,
                    )

                    Text(
                        text = "And note your ideas",
                        style = TextStyles.textBase,
                        color = ColorPalette.NeutralBaseGrey,
                    )
                }

                TextInputField(
                    value = state.username,
                    onValueChange = viewModel::setUsername,
                    label = "Username",
                    placeholderText = "foratik",
                    isError = state.usernameError != null,
                    caption = state.usernameError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next // Move to next field
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                TextInputField(
                    value = state.password,
                    onValueChange = viewModel::setPassword,
                    label = "Password",
                    isError = state.passwordError != null,
                    caption = state.passwordError,
                    placeholderText = "********",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done // Move to next field
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (!state.isLoggingIn) {
                                viewModel.login()
                            }
                        },
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                )

                Button(
                    "Login",
                    ButtonStyle.PRIMARY,
                    ButtonSize.BLOCK,
                    viewModel::login,
                    enabled = !state.isLoggingIn,
                    icon = Heroicons.Solid.ArrowRight,
                    iconPosition = IconPosition.RIGHT,
                )

                if (state.error != null) {
                    Text(
                        text = state.error.orEmpty(),
                        color = ColorPalette.ErrorBase,
                        style = TextStyles.textXs,
                        modifier = Modifier,
                    )
                }

                DividerWithText(
                    text = "Or",
                    textColor = ColorPalette.NeutralDarkGrey,
                    lineColor = ColorPalette.NeutralLightGrey,
                )

                Button(
                    "Don’t have any account? Register here",
                    ButtonStyle.TRANSPARENT,
                    ButtonSize.BLOCK,
                    viewModel::goToRegisterClicked,
                )
            }
        }
    }
}
