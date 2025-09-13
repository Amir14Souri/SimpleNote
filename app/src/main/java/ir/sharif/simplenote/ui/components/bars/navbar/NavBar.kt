package ir.sharif.simplenote.ui.components.controls.navbar

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
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ChevronLeft
import ir.sharif.simplenote.ui.components.controls.link.LinkComponent
import ir.sharif.simplenote.ui.components.controls.link.LinkIconPosition
import ir.sharif.simplenote.ui.components.controls.link.LinkSize
import ir.sharif.simplenote.ui.components.controls.link.LinkUnderlineStyle
import ir.sharif.simplenote.ui.theme.TextStyles


@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    linkText: String,
    onLink: () -> Unit,
    text: String? = null,
    borderBottom: Boolean = false,
) {
    Column {
        Box (
            modifier = modifier.fillMaxWidth().padding(16.dp),
        ) {
            LinkComponent(
                modifier = Modifier.align(Alignment.CenterStart),
                text = linkText,
                onClick = onLink,
                size = LinkSize.Large,
                icon = Heroicons.Solid.ChevronLeft,
                iconPosition = LinkIconPosition.Left,
                underlineStyle = LinkUnderlineStyle.NoUnderline,
            )

            if (text != null) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = text,
                    style = TextStyles.textBaseMedium.copy(
                        color = Color.Black
                    ),
                )
            }
        }
        if (borderBottom) {
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color(0xFFEFEEF0))
        }
    }
}
