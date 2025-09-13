package ir.sharif.simplenote.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.sharif.simplenote.R
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun EmptyNotesContent(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(top=153.dp, start = 60.dp, end = 60.dp)) {
        Image(painterResource(R.drawable.start_journey),
            contentDescription = "a person starting a journey",
            modifier = Modifier.size(240.dp))
        Spacer(modifier = Modifier.size(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Your Journey",
                style = TextStyles.textXlBold.copy(color= ColorPalette.NeutralBlack))

            Spacer(modifier = Modifier.size(16.dp))

            Text("Every big step start with small step.\n" +
                    "Notes your first idea and start\n" +
                    "your journey!",
                style = TextStyles.textSm.copy(color= ColorPalette.NeutralDarkGrey),
                textAlign = TextAlign.Center)

        }

    }
    Box(modifier = modifier.fillMaxSize().padding(bottom = 50.dp),
        contentAlignment = Alignment.BottomCenter) {

        Box(modifier = Modifier.padding(start = 118.dp)) {

            Image(painterResource(R.drawable.direction),
                "arrow pointing toward create button",
                modifier = Modifier.height(100.dp))
        }
    }
}