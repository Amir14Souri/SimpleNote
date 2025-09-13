@file:OptIn(ExperimentalMaterial3Api::class)

package ir.sharif.simplenote.ui.components.overlays.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.ModalBottomSheet as MaterialModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Trash
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.XMark
import ir.sharif.simplenote.ui.components.controls.dividerwithtext.DividerWithText
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun ModalBottomSheet(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = {
            false
        }
    )

    MaterialModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        sheetState = sheetState,
    ) {
        Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    title,
                    style = TextStyles.textBaseMedium.copy(color = ColorPalette.NeutralBlack)
                )

                IconButton(
                    onClick = onDismiss, modifier = Modifier.size(24.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = ColorPalette.NeutralLightGrey,
                        contentColor = ColorPalette.NeutralDarkGrey,
                    )
                ) {
                    Icon(
                        Heroicons.Outline.XMark,
                        contentDescription = "close icon",
                    )
                }

            }


            HorizontalDivider()

            Spacer(modifier = Modifier.size(8.dp))

            Column(
                content = content
            )
        }
    }
}

@Preview
@Composable
fun ModalBottomSheetPreview() {
    ModalBottomSheet(title = "some title", onDismiss = {}) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Heroicons.Outline.Trash, "delete icon",
                tint = ColorPalette.ErrorBase
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                "Delete Note", style = TextStyles.textBaseMedium.copy(
                    color = ColorPalette.ErrorBase
                )
            )
        }
    }
}