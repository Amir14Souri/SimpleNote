package ir.sharif.simplenote.ui.components.inputs.textinput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ir.sharif.simplenote.ui.theme.TextStyles
import ir.sharif.simplenote.ui.theme.ColorPalette


/**
 * A custom, enhanced text input field with full layout control for supporting text.
 *
 * @param value The current text value of the field.
 * @param onValueChange The callback that is triggered when the input service updates the text.
 * @param modifier The Modifier to be applied to the layout.
 * @param label The optional text to display in the static label above the field.
 * @param isError Indicates if the text field's current value is in an error state.
 * @param placeholderText The text to be displayed as a placeholder when the field is empty.
 * @param caption The optional helper text to be displayed below the field.
 * @param keyboardOptions Software keyboard options that contains configuration such as KeyboardType.
 * @param visualTransformation Transforms the visual representation of the input value.
 */
@Composable
fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    isError: Boolean = false,
    placeholderText: String = "",
    caption: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    when {
        isError -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = TextStyles.textBaseMedium,
                color = ColorPalette.NeutralBlack,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        OutlinedTextField(
            value = value,
            textStyle = TextStyles.textBase,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            isError = isError,
            singleLine = true,
            placeholder = { Text(text = placeholderText) },
            supportingText = null,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = ColorPalette.ErrorBase,
                focusedBorderColor = ColorPalette.PrimaryBase,
                unfocusedBorderColor = ColorPalette.NeutralBaseGrey,

                unfocusedPlaceholderColor = ColorPalette.NeutralBaseGrey,
                disabledPlaceholderColor = ColorPalette.NeutralBaseGrey,
                focusedPlaceholderColor = ColorPalette.NeutralBaseGrey,

                errorTextColor = ColorPalette.NeutralBlack,
                focusedTextColor = ColorPalette.NeutralBlack,
                unfocusedTextColor = ColorPalette.NeutralBlack,
                disabledTextColor = ColorPalette.NeutralBaseGrey,
            ),
            shape = RoundedCornerShape(8.dp)
        )

        if (caption != null) {
            Text(
                text = caption,
                color = if (isError) ColorPalette.ErrorBase else ColorPalette.NeutralBaseGrey,
                style = TextStyles.text2Xs,
                modifier = Modifier.padding(
                    top = 12.dp,
                    start = 0.dp
                )
            )
        }
    }
}
