package ir.sharif.simplenote.ui.components.controls.button

import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ArrowRight
import ir.sharif.simplenote.ui.components.IconPosition
import ir.sharif.simplenote.ui.theme.SimpleNoteTheme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.CreditCard


@Preview(showBackground = true, name = "Primary Buttons")
@Composable
fun ButtonPreviewPrimary() {
    SimpleNoteTheme { // Wrap with your app's theme for accurate colors/fonts
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // --- Primary Buttons ---
                Button(
                    text = "Primary Button",
                    style = ButtonStyle.PRIMARY,
                    size = ButtonSize.BLOCK,
                    onClick = {}
                )
                Button(
                    text = "With Left Icon",
                    style = ButtonStyle.PRIMARY,
                    size = ButtonSize.BLOCK,
                    icon = Heroicons.Solid.ArrowRight,
                    iconPosition = IconPosition.SIDE_LEFT,
                    onClick = {}
                )
                Button(
                    text = "Disabled Primary",
                    style = ButtonStyle.PRIMARY,
                    size = ButtonSize.BLOCK,
                    icon = Heroicons.Solid.ArrowRight,
                    enabled = false,
                    onClick = {}
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Secondary Buttons")
@Composable
fun ButtonPreviewSecondary() {
    SimpleNoteTheme {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Secondary Buttons ---
            Button(
                text = "Secondary Button",
                style = ButtonStyle.SECONDARY,
                size = ButtonSize.BLOCK,
                onClick = {}
            )
            Button(
                text = "With Right Icon",
                style = ButtonStyle.SECONDARY,
                size = ButtonSize.BLOCK,
                icon = Heroicons.Solid.ArrowRight,
                iconPosition = IconPosition.RIGHT,
                onClick = {}
            )
            Button(
                text = "Disabled Secondary",
                style = ButtonStyle.SECONDARY,
                size = ButtonSize.BLOCK,
                icon = Heroicons.Solid.ArrowRight,
                enabled = false,
                onClick = {}
            )
        }
    }
     }
}


@Preview(showBackground = true, name = "Block & Far Right Icon")
@Composable
fun ButtonPreviewBlock() {
    SimpleNoteTheme {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Block Buttons ---
            Button(
                text = "Block Button",
                style = ButtonStyle.PRIMARY,
                size = ButtonSize.BLOCK,
                onClick = {}
            )

            // The key test for the IconPosition.RIGHT
            Button(
                text = "Continue",
                style = ButtonStyle.PRIMARY,
                size = ButtonSize.BLOCK,
                icon = Heroicons.Solid.CreditCard,
                iconPosition = IconPosition.RIGHT,
                onClick = {}
            )
            Button(
                text = "Submit",
                style = ButtonStyle.SECONDARY,
                size = ButtonSize.BLOCK,
                icon = Heroicons.Solid.ArrowRight,
                iconPosition = IconPosition.RIGHT,
                onClick = {}
            )
        }
    }
    }
}