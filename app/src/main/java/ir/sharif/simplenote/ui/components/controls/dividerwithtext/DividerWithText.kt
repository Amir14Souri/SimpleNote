package ir.sharif.simplenote.ui.components.controls.dividerwithtext

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.sharif.simplenote.ui.theme.TextStyles

/**
 * A composable that displays a Divider with text in the middle.
 * This is a common pattern for "or" separators in forms.
 *
 * @param modifier The modifier to be applied to the component.
 * @param text The text to be displayed in the middle of the dividers.
 * @param color The color of the text and the dividers. Defaults to `MaterialTheme.colorScheme.onSurfaceVariant`.
 * @param thickness The thickness of the dividers. Defaults to `1.dp`.
 * @param horizontalPadding The padding applied to the left and right of the text. Defaults to `8.dp`.
 */
@Composable
fun DividerWithText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    lineColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    thickness: Dp = 1.dp,
    horizontalPadding: Dp = 16.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = thickness,
            color = lineColor
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = horizontalPadding),
            color = textColor,
            style = TextStyles.text2XsMedium
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = thickness,
            color = lineColor
        )
    }
}
