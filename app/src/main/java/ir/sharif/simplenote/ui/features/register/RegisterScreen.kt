package ir.sharif.simplenote.ui.features.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
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
import ir.sharif.simplenote.ui.components.controls.navbar.NavBar
import ir.sharif.simplenote.ui.components.inputs.textinput.TextInputField
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun RegisterScreen(
    onNavigateBackToLogin: () -> Unit = {},
    viewModel: RegisterViewModel  = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when(event) {
                is RegisterViewModel.NavigationEvent.ToLogin -> onNavigateBackToLogin()
            }
        }
    }

    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsState().value
    val focusManager = LocalFocusManager.current

    Scaffold (
        topBar = {
            NavBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars.only(
                    WindowInsetsSides.Top)),
                linkText = "Back to Login",
                onLink = onNavigateBackToLogin
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.verticalScroll(scrollState)
            ) {

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Register",
                        style = TextStyles.text2XlBold,
                    )

                    Text(
                        text = "And start taking notes",
                        style = TextStyles.textBase,
                        color = ColorPalette.NeutralDarkGrey,
                    )
                }


                TextInputField(
                    value = uiState.firstName,
                    onValueChange = viewModel::setFirstName,
                    label = "First Name",
                    placeholderText ="Amirhossein",
                    caption = uiState.firstNameError,
                    isError = uiState.firstNameError != null,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                TextInputField(
                    value = uiState.lastName,
                    onValueChange = viewModel::setLastName,
                    label = "Last Name",
                    placeholderText ="Souri",
                    caption = uiState.lastNameError,
                    isError = uiState.lastNameError != null,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                TextInputField(
                    value = uiState.username,
                    onValueChange = viewModel::setUsername,
                    label = "Username",
                    placeholderText = "@amir7souri",
                    caption = uiState.usernameError,
                    isError = uiState.usernameError != null,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                TextInputField(
                    value = uiState.email,
                    onValueChange = viewModel::setEmail,
                    label = "Email Address",
                    placeholderText = "amirhsnsouri@gmail.com",
                    isError = uiState.emailError != null,
                    caption =  uiState.emailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next // Move to next field
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                TextInputField(
                    value = uiState.password,
                    onValueChange = viewModel::setPassword,
                    label = "Password",
                    isError = uiState.passwordError != null,
                    placeholderText = "********",
                    caption = uiState.passwordError,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next // Move to next field
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                TextInputField(
                    value = uiState.passwordConfirm,
                    onValueChange = viewModel::setPasswordConfirm,
                    label = "Retype Password",
                    isError = uiState.passwordConfirmError != null,
                    caption = uiState.passwordConfirmError,
                    placeholderText = "********",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done // This is the final action
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus() // Hides the keyboard
                            if (!uiState.isCreating) {
                                viewModel.register()
                            }
                        }
                    )
                )


                if (uiState.error != null) {
                    Text(
                        text = uiState.error,
                        color = ColorPalette.ErrorBase,
                        style = TextStyles.text2Xs,
                        modifier = Modifier,
                    )
                }

                Button(
                    "Register",
                    ButtonStyle.PRIMARY,
                    ButtonSize.BLOCK,
                    viewModel::register,
                    enabled = !uiState.isCreating,
                    icon = Heroicons.Solid.ArrowRight,
                    iconPosition = IconPosition.RIGHT,
                )

                Button(
                    "Already have an account? Login here",
                    onClick = onNavigateBackToLogin,
                    style = ButtonStyle.TRANSPARENT,
                    size = ButtonSize.BLOCK,
                )
            }
        }
    }

}