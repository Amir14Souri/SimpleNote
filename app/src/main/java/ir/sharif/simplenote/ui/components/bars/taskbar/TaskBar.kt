package ir.sharif.simplenote.ui.components.bars.taskbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Trash
import ir.sharif.simplenote.ui.components.bars.taskbar.button.TaskBarButton
import ir.sharif.simplenote.ui.theme.TextStyles


@Composable
fun TaskBar(
    text: String,
    onButtonClick: () -> Unit = {},
    borderTop: Boolean = true,
    showButton: Boolean = true,
) {
    Column {
        if (borderTop) {
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color(0xFFEFEEF0))
        }
        Box (
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (showButton) {
                TaskBarButton(
                    onButtonClick,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    icon = Heroicons.Solid.Trash
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterStart).fillMaxWidth().padding(16.dp),
                text = text,
                style = TextStyles.text2Xs.copy(
                    color = Color.Black
                )
            )
        }
    }
}
