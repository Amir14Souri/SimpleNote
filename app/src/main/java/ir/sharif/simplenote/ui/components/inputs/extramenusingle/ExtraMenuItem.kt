package ir.sharif.simplenote.ui.components.menu.extramenusingle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ChevronLeft
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ChevronRight
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Trash
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles
import java.util.logging.Logger

enum class ExtraMenuItemStyle {
    Danger,
    Arrow
}

@Preview
@Composable
fun ExtraMenuItem(
    text: String = "Text",
    onClick: () -> Unit = {},
    leftIcon: ImageVector? = Heroicons.Solid.Trash,
    style: ExtraMenuItemStyle = ExtraMenuItemStyle.Danger,
    enable: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = when {
        style == ExtraMenuItemStyle.Danger -> {
            when {
                !enable -> ColorPalette.NeutralWhite
                isPressed -> ColorPalette.ErrorLight
                else -> ColorPalette.NeutralWhite
            }
        }
        isPressed -> ColorPalette.PrimaryBackground
        else -> ColorPalette.NeutralWhite
    }

    val contentColor = when{
        !enable -> ColorPalette.NeutralBaseGrey
        style == ExtraMenuItemStyle.Danger -> ColorPalette.ErrorBase
        else -> ColorPalette.NeutralBlack
    }

    Logger.getLogger("s").info(backgroundColor.toString())

    Surface (
        modifier = Modifier
            .fillMaxWidth().height(56.dp).clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp),
    ) {
        Box (
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Row (
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (leftIcon != null) {
                    Icon(
                        leftIcon,
                        "menuItemIcon",
                        tint = contentColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Text(
                    text = text,
                    style = TextStyles.textBaseMedium.copy(
                        color = contentColor
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            if (style == ExtraMenuItemStyle.Arrow) {
                Icon(
                    Heroicons.Solid.ChevronRight,
                    "MenuItemIcon",
                    tint = ColorPalette.NeutralDarkGrey,
                    modifier = Modifier.size(16.dp).align(Alignment.CenterEnd)
                )
            }
        }

    }
}
