package ir.sharif.simplenote.ui.components.inputs.freetextareainput

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column // For Preview purposes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles // Assuming this is your custom TextStyle object

/**
 * A highly opinionated, minimal (borderless) single-line text input using BasicTextField.
 * This component provides core text input functionality designed specifically for a single-line
 * input like a title, without any default visual decorations like borders, backgrounds, or labels.
 *
 * All styling (text style, color), line behavior (singleLine = true), and keyboard options
 * are hardcoded internally to ensure consistent behavior and appearance across the application.
 *
 * @param value The current text value of the input field.
 * @param onValueChange A callback that is triggered when the text changes.
 * @param modifier The modifier to be applied to the underlying BasicTextField.
 *                 Use this for padding, width, etc.
 * @param placeholder The hint text displayed when the input is empty. If null, no placeholder.
 */
@Composable
fun FreeTextAreaInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = TextStyles.textBase.copy(
            color = ColorPalette.NeutralDarkGrey
        ),
        singleLine = false,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),

        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = TextStyles.textBase.copy(
                            color = ColorPalette.NeutralBaseGrey
                        )
                    )
                }
                innerTextField()
            }
        }
    )
}

/**
 * Preview for the FreeTextAreaInput component.
 * Demonstrates both empty (with placeholder) and filled states of this single-line input.
 */
@Preview(showBackground = true, name = "FreeTextAreaInput Preview - Hardcoded Single-Line")
@Composable
fun FreeTextAreaInputPreview() {
    var textValue1 by remember { mutableStateOf("") }
    var textValue2 by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Apply screen-level padding here
    ) {
        FreeTextAreaInput(
            value = textValue1,
            onValueChange = { textValue1 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = "Enter your title here...",
        )
        FreeTextAreaInput(
            value = textValue2,
            onValueChange = { textValue2 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = "Enter your title here...",
        )
    }
}
