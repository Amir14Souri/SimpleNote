package ir.sharif.simplenote.ui.components.bars.tabbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Plus
import ir.sharif.simplenote.ui.theme.ColorPalette

@Composable
fun TabBar(
    onAddClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Transparent)
        ) {

            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentColor = ColorPalette.PrimaryBase,
                color = ColorPalette.NeutralWhite,
                shadowElevation = 8.dp
            ) {
                TabNavigationBar(onSettingsClicked, modifier)
            }

            Surface(
                modifier= Modifier.align(Alignment.TopCenter).offset(x = 0.dp, (-36).dp),
                border = BorderStroke(8.dp, ColorPalette.PrimaryBackground),
                color = Transparent,
                contentColor = ColorPalette.NeutralWhite,
                shape = CircleShape,

            ) {
                FloatingActionButton (
                    modifier = modifier.padding(8.dp).size(64.dp),
                    onClick = onAddClicked,
                    containerColor = ColorPalette.PrimaryBase,
                    contentColor = ColorPalette.NeutralWhite,
                    shape = CircleShape
                ) {
                    Icon(
                        Heroicons.Outline.Plus,
                        contentDescription = "Add",
                        modifier = modifier.size(32.dp)
                    )
                }
            }
    }
}
