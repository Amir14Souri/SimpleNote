package ir.sharif.simplenote.ui.features.onboarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.woowla.compose.icon.collections.heroicons.Heroicons
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.R
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ArrowRight
import ir.sharif.simplenote.ui.components.IconPosition
import ir.sharif.simplenote.ui.components.controls.button.Button
import ir.sharif.simplenote.ui.components.controls.button.ButtonSize
import ir.sharif.simplenote.ui.components.controls.button.ButtonStyle
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun OnBoardingScreen(onNavigateToLogin: () -> Unit) {
    Scaffold (
        containerColor = ColorPalette.PrimaryBase,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding).padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Box(
                    modifier = Modifier.padding(
                        start = 24.dp, end = 24.dp,
                        top = 128.dp, bottom = 24.dp
                    )
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.onboarding_illustration),
                        contentDescription = "onboarding illustration",
                        modifier = Modifier.size(280.dp)
                    )
                }

                Text(
                    "Jot down anything you want to achieve, today or in the future",
                    style = TextStyles.textLgBold,
                    color = ColorPalette.NeutralWhite,
                    textAlign = TextAlign.Center
                )

            }

            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    "Let's Get Started",
                    ButtonStyle.SECONDARY,
                    ButtonSize.BLOCK,
                    onNavigateToLogin,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 54.dp)
                        .align(Alignment.BottomCenter),
                    icon = Heroicons.Solid.ArrowRight,
                    iconPosition = IconPosition.RIGHT,
                )
            }
        }
    }
}