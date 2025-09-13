package ir.sharif.simplenote.ui.components.overlays.bottommodalnotification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.sharif.simplenote.R
import ir.sharif.simplenote.ui.components.controls.button.Button
import ir.sharif.simplenote.ui.components.controls.button.ButtonSize
import ir.sharif.simplenote.ui.components.controls.button.ButtonStyle
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BottomModalNotification(
    notificationTitle: String = "Notes Pinned Successfully",
    notificationContent: String = "This note already displayed on pinned section",
    buttonText: String = "Close",
    onButtonClick: () -> Unit = {},
    showModal: Boolean = true,
    onDismissRequest: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    if (showModal) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissRequest()
            },
            sheetState = sheetState,
            dragHandle = null,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                                onDismissRequest()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close",
                            tint = ColorPalette.NeutralBaseGrey
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.success),
                    contentDescription = "Success illustration",
                    modifier = Modifier.size(160.dp)
                )

                Text(
                    text = notificationTitle,
                    style = TextStyles.textLgBold.copy(
                        color = ColorPalette.NeutralBlack
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = notificationContent,
                    textAlign = TextAlign.Center,
                    style = TextStyles.textBase.copy(
                        color = ColorPalette.NeutralDarkGrey
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Button(
                    text = buttonText,
                    size = ButtonSize.SMALL,
                    style = ButtonStyle.PRIMARY,

                    onClick = {
                        onButtonClick()
                        scope.launch {
                            sheetState.hide()
                            onDismissRequest()
                        }
                    }
                )

            }
        }
    }
}
