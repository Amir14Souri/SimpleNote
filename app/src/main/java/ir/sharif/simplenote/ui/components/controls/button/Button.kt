package ir.sharif.simplenote.ui.components.controls.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.Button as MaterialButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ir.sharif.simplenote.ui.components.IconPosition
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Immutable
private data class ButtonAttributes(
    val containerColor: Color,
    val contentColor: Color,
    val border: BorderStroke?,

    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val disabledBorder: BorderStroke?,

    val pressedContainerColor: Color,
    val pressedContentColor: Color,
    val pressedBorder: BorderStroke?,

    val shape: Shape,
    val contentPadding: PaddingValues,
    val modifier: Modifier,
)

@Composable
private fun resolveButtonAttributes(
    style: ButtonStyle,
    size: ButtonSize,
): ButtonAttributes {

    val containerColor = when (style) {
        ButtonStyle.PRIMARY -> ColorPalette.PrimaryBase
        ButtonStyle.SECONDARY -> ColorPalette.NeutralWhite
        ButtonStyle.TRANSPARENT -> Color.Transparent
    }

    val contentColor = when (style) {
        ButtonStyle.PRIMARY -> ColorPalette.NeutralWhite
        ButtonStyle.SECONDARY -> ColorPalette.PrimaryBase
        ButtonStyle.TRANSPARENT -> ColorPalette.PrimaryBase
    }


    val pressedContainerColor = when (style) {
        ButtonStyle.PRIMARY -> ColorPalette.PrimaryDark
        ButtonStyle.SECONDARY -> ColorPalette.PrimaryDark
        ButtonStyle.TRANSPARENT -> ColorPalette.PrimaryLight
    }

    val pressedContentColor = when (style) {
        ButtonStyle.PRIMARY -> ColorPalette.NeutralWhite
        ButtonStyle.SECONDARY -> ColorPalette.PrimaryDark
        ButtonStyle.TRANSPARENT -> ColorPalette.PrimaryBase
    }


    val disabledContainerColor = when (style) {
        ButtonStyle.PRIMARY -> ColorPalette.NeutralBaseGrey
        ButtonStyle.SECONDARY -> ColorPalette.NeutralWhite
        ButtonStyle.TRANSPARENT -> Color.Transparent
    }

    val disabledContentColor = when (style) {
        ButtonStyle.PRIMARY -> ColorPalette.NeutralLightGrey
        ButtonStyle.SECONDARY -> ColorPalette.NeutralBaseGrey
        ButtonStyle.TRANSPARENT -> ColorPalette.NeutralBaseGrey
    }

    val sizeModifier = when (size) {
        ButtonSize.BLOCK -> Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        else -> Modifier
    }

    val border = when(style) {
        ButtonStyle.SECONDARY -> BorderStroke(1.dp, ColorPalette.PrimaryBase)
        else -> null
    }

    val pressedBorder = when(style) {
        ButtonStyle.SECONDARY -> BorderStroke(1.dp, ColorPalette.PrimaryDark)
        else -> null
    }

    val disabledBorder = when(style) {
        ButtonStyle.SECONDARY -> BorderStroke(1.dp, ColorPalette.NeutralBaseGrey)
        else -> null
    }

    return ButtonAttributes(
        containerColor = containerColor,
        contentColor = contentColor,
        border = border,

        pressedContentColor = pressedContentColor,
        pressedContainerColor = pressedContainerColor,
        pressedBorder = pressedBorder,

        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
        disabledBorder = disabledBorder,

        shape = RoundedCornerShape(100.dp),
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 16.dp,
        ),
        modifier = sizeModifier,
    )
}

@Composable
fun Button(
    text: String,
    style: ButtonStyle,
    size: ButtonSize,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    // Assuming IconPosition has at least RIGHT, SIDE_LEFT, SIDE_RIGHT
    iconPosition: IconPosition = IconPosition.SIDE_LEFT,
) {
    val attributes = resolveButtonAttributes(style, size)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animatedContainerColor by animateColorAsState(
        targetValue = if (isPressed) {
            attributes.pressedContainerColor
        } else {
            attributes.containerColor
        }, label = "ButtonContainerColor"
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (isPressed) {
            attributes.pressedContentColor
        } else {
            attributes.contentColor
        }, label = "ButtonContentColor"
    )

    val border = when {
        !enabled -> attributes.disabledBorder
        isPressed -> attributes.pressedBorder
        else -> attributes.border
    }

    MaterialButton(
        onClick = onClick,
        modifier = modifier.then(attributes.modifier),
        enabled = enabled,
        shape = attributes.shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedContainerColor,
            contentColor = animatedContentColor,
            disabledContainerColor = attributes.disabledContainerColor,
            disabledContentColor = attributes.disabledContentColor
        ),
        contentPadding = attributes.contentPadding,
        border = border
    ) {
        if (icon != null && (iconPosition == IconPosition.RIGHT || iconPosition == IconPosition.LEFT)) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    style = TextStyles.textBaseMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    imageVector = icon,
                    contentDescription = "icon",
                    modifier = when(iconPosition) {
                        IconPosition.LEFT -> Modifier.align(Alignment.CenterStart)
                        IconPosition.RIGHT -> Modifier.align(Alignment.CenterEnd)
                        else -> Modifier
                    }
                )
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null && iconPosition == IconPosition.SIDE_LEFT) {
                    Icon(imageVector = icon, contentDescription = "icon")
                    Spacer(Modifier.width(10.dp))
                }

                Text(
                    text = text,
                    style = TextStyles.textBaseMedium,
                )

            }
        }
    }
}
