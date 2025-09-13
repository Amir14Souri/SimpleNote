package ir.sharif.simplenote.ui.components.controls.link

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ArrowRight
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

enum class LinkUnderlineStyle {
    Underline,
    NoUnderline
}

enum class LinkSize {
    Small,
    Large
}

enum class LinkIconPosition {
    Left,
    Right
}

/**
 * A customizable Link component following Material 3 design principles.
 * It can display text with an optional icon, and supports different sizes and underline styles.
 *
 * @param text The text to be displayed in the link.
 * @param onClick The callback to be invoked when the link is clicked.
 * @param modifier The modifier to be applied to the component.
 * @param underlineStyle Specifies whether the text should be underlined. Defaults to [LinkUnderlineStyle.Underline].
 * @param size Specifies the size of the text and icon. Defaults to [LinkSize.Small].
 * @param icon Optional [ImageVector] to be displayed alongside the text. Defaults to `null`.
 * @param iconPosition Specifies the position of the icon relative to the text. Defaults to [LinkIconPosition.Left].
 */
@Composable
fun LinkComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    underlineStyle: LinkUnderlineStyle = LinkUnderlineStyle.Underline,
    size: LinkSize = LinkSize.Small,
    icon: ImageVector? = null,
    iconPosition: LinkIconPosition = LinkIconPosition.Left,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = when {
        !enabled -> ColorPalette.NeutralBaseGrey
        isPressed -> ColorPalette.PrimaryDark
        else -> ColorPalette.PrimaryBase
    }

    val baseTextStyle = when (size) {
        LinkSize.Large -> TextStyles.textBaseMedium
        LinkSize.Small -> TextStyles.text2XsBold
    }

    val finalTextStyle = baseTextStyle.copy(
        color = color,
        textDecoration = if (underlineStyle == LinkUnderlineStyle.Underline) TextDecoration.Underline else null
    )

    // Determine the appropriate icon size based on the chosen text size for visual balance.
    val iconSizeModifier = Modifier.size(
        when (size) {
            LinkSize.Small -> 15.dp // Common icon size for 12-14sp text
            LinkSize.Large -> 20.dp
        }
    )

    // The entire Row is made clickable.
    Row(
        modifier = modifier
            .clickable(onClick = onClick) // Makes the Row clickable
            .semantics(mergeDescendants = true) { // Merges descendants for accessibility (e.g., icon and text read as one button)
                role = Role.Button // Specifies that this composable behaves like a button for accessibility services
            },
        verticalAlignment = Alignment.CenterVertically, // Vertically center the icon and text
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Add a small space between the icon and text
    ) {
        // Render the icon if provided and its position is Left
        if (icon != null && iconPosition == LinkIconPosition.Left) {
            Icon(
                imageVector = icon,
                contentDescription = null, // The content description is provided by the whole Row's text
                tint = color,
                modifier = iconSizeModifier
            )
        }

        // Render the link text
        Text(
            text = text,
            style = finalTextStyle,
            maxLines = 1, // Links typically appear on a single line
            overflow = TextOverflow.Ellipsis // Truncate text with ellipsis if it exceeds maxLines
        )

        // Render the icon if provided and its position is Right
        if (icon != null && iconPosition == LinkIconPosition.Right) {
            Icon(
                imageVector = icon,
                contentDescription = null, // The content description is provided by the whole Row's text
                tint = color,
                modifier = iconSizeModifier
            )
        }
    }
}


/**
 * Preview for the LinkComponent, showcasing various configurations.
 */
@Preview(showBackground = true, name = "LinkComponent Preview")
@Composable
fun LinkComponentPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        LinkComponent(text = "Click me", onClick = { /* Handle click */ })
        Spacer(Modifier.padding(vertical = 8.dp))


        LinkComponent(
            text = "View Details",
            onClick = { /* Handle click */ },
            size = LinkSize.Large,
            underlineStyle = LinkUnderlineStyle.NoUnderline,
            icon = Heroicons.Solid.ArrowRight, // Example Material Icon
            iconPosition = LinkIconPosition.Left
        )
        Spacer(Modifier.padding(vertical = 8.dp))


        LinkComponent(
            text = "Read More",
            onClick = { /* Handle click */ },
            size = LinkSize.Small,
            underlineStyle = LinkUnderlineStyle.Underline,
            icon = Heroicons.Solid.ArrowRight, // Example Material Icon
            iconPosition = LinkIconPosition.Right
        )
        Spacer(Modifier.padding(vertical = 8.dp))

        LinkComponent(
            text = "Settings",
            onClick = { /* Handle click */ },
            size = LinkSize.Large,
            underlineStyle = LinkUnderlineStyle.NoUnderline,
        )
        Spacer(Modifier.padding(vertical = 8.dp))

        LinkComponent(
            text = "Warning",
            onClick = { /* Handle click */ },
            size = LinkSize.Small,
            icon = Heroicons.Solid.ArrowRight,
            iconPosition = LinkIconPosition.Left,
        )
    }
}

