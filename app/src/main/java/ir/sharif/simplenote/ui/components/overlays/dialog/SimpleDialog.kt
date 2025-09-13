package ir.sharif.simplenote.ui.components.overlays.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.sharif.simplenote.ui.components.controls.button.Button
import ir.sharif.simplenote.ui.components.controls.button.ButtonSize
import ir.sharif.simplenote.ui.components.controls.button.ButtonStyle
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun SimpleDialog(
    dialogTitle: String = "Log Out",
    dialogText: String = "Are you sure you want to log out from the application?",
    onDismissRequest: () -> Unit = {},
    dismissText: String = "Cancel",
    onConfirmation: () -> Unit = {},
    confirmText: String = "Yes",
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .width(280.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = ColorPalette.NeutralWhite
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dialogTitle,
                    style = TextStyles.textLgBold.copy(
                        color = ColorPalette.NeutralBlack
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = dialogText,
                    textAlign = TextAlign.Center,
                    style = TextStyles.textBase.copy(
                        color = ColorPalette.NeutralDarkGrey
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Spacer(modifier = Modifier.height(48.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        text = dismissText,
                        style = ButtonStyle.SECONDARY,
                        size = ButtonSize.SMALL,
                        onClick = onDismissRequest,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        text = confirmText,
                        style = ButtonStyle.PRIMARY,
                        size = ButtonSize.SMALL,
                        onClick = onConfirmation,
                    )
                }
            }
        }
    }
}