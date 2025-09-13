package ir.sharif.simplenote.ui.components.bars.tabbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Cog8Tooth
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Home
import ir.sharif.simplenote.ui.theme.TextStyles
@Composable
fun TabNavigationBar(
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .padding(
                WindowInsets.systemBars.only(WindowInsetsSides.Bottom).asPaddingValues())
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = modifier
                .width(52.dp)
                .aspectRatio(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Heroicons.Solid.Home, contentDescription = "Home",
                modifier = modifier.size(32.dp)
            )
            Text(text = "Home", style = TextStyles.textXs, textAlign = TextAlign.Center)
        }

        Column(
            modifier = modifier
                .width(52.dp)
                .aspectRatio(1f).clickable(
                    onClick = onSettingsClicked
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                Heroicons.Outline.Cog8Tooth, contentDescription = "Cog",
                modifier = modifier.size(32.dp)
            )
            Text(text = "Settings", style = TextStyles.textXs, textAlign = TextAlign.Center)
        }
    }
}