package ir.sharif.simplenote.ui.components.bars.taskbar.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column // For preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Trash
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.SimpleNoteTheme // Assuming your app's Material Theme wrapper

/**
 * Defines the colors for a [TaskBarButton] in its different states.
 *
 * @param backgroundColor The background color of the button in its normal (enabled, unpressed) state.
 * @param contentColor The content (e.g., icon) color of the button in its normal state.
 * @param disabledBackgroundColor The background color when the button is disabled.
 * @param disabledContentColor The content color when the button is disabled.
 * @param pressedBackgroundColor An optional background color when the button is pressed.
 *                                If null, the normal background color will be used, relying on the ripple for feedback.
 * @param pressedContentColor An optional content color when the button is pressed.
 *                             If null, the normal content color will be used.
 */
data class TaskBarButtonColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val disabledBackgroundColor: Color,
    val disabledContentColor: Color,
    val pressedBackgroundColor: Color? = null,
    val pressedContentColor: Color? = null
)

/**
 * Provides default [TaskBarButtonColors] based on [MaterialTheme.colorScheme].
 * Uses `surfaceVariant` for background and `onSurfaceVariant` for content by default.
 */
@Composable
fun defaultTaskBarButtonColors(): TaskBarButtonColors {
    val defaultBg = MaterialTheme.colorScheme.surfaceVariant
    val defaultContent = MaterialTheme.colorScheme.onSurfaceVariant
    return remember(defaultBg, defaultContent) {
        TaskBarButtonColors(
            backgroundColor = ColorPalette.PrimaryBase,
            contentColor = Color.White,
            // Disabled colors often use lower alpha or a different subdued color
            disabledBackgroundColor = ColorPalette.NeutralBaseGrey,
            disabledContentColor = ColorPalette.NeutralDarkGrey, // Standard disabled alpha for text/icons
            // Provide subtle visual change for pressed state
            pressedBackgroundColor = ColorPalette.PrimaryDark,
            pressedContentColor = Color.White
        )
    }
}

/**
 * A square-shaped button suitable for taskbars or toolbars.
 *
 * @param onClick The lambda to be invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param enabled If false, the button will be disabled and non-clickable, displaying its disabled colors. Defaults to true.
 * @param colors The [TaskBarButtonColors] to customize the button's appearance in different states.
 */
@Composable
fun TaskBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: TaskBarButtonColors = defaultTaskBarButtonColors(),
    icon: ImageVector
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentBackgroundColor = when {
        !enabled -> colors.disabledBackgroundColor
        isPressed && colors.pressedBackgroundColor != null -> colors.pressedBackgroundColor
        else -> colors.backgroundColor
    }

    val currentContentColor = when {
        !enabled -> colors.disabledContentColor
        isPressed && colors.pressedContentColor != null -> colors.pressedContentColor
        else -> colors.contentColor
    }

    Surface(
        modifier = modifier
            .size(48.dp)
            .graphicsLayer(alpha = 1f, rotationZ = 0f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
            .semantics(mergeDescendants = true) {
                role = Role.Button
            },
        shape = RectangleShape,
        color = currentBackgroundColor,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, "icon", tint = currentContentColor)
        }
    }
}


@Preview(showBackground = true, name = "TaskBarButton Preview")
@Composable
fun TaskBarButtonPreview() {
    SimpleNoteTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Custom Icon/Colors", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            TaskBarButton(
                onClick = { /* Won't be called */ },
                enabled = true,
                icon = Heroicons.Outline.Trash
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Disabled State", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            TaskBarButton(
                onClick = { /* Won't be called */ },
                enabled = false,
                icon = Heroicons.Outline.Trash
            )
        }
    }
}
