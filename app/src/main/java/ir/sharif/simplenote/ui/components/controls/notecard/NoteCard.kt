package ir.sharif.simplenote.ui.components.controls.notecard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.sharif.simplenote.domain.model.Note // Make sure to use your actual Note data class
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.SimpleNoteTheme // Assuming you have a theme
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick=onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Shadow
        colors = CardDefaults.cardColors(
            containerColor = ColorPalette.SecondaryLight,
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = note.title,
                style = TextStyles.textBaseMedium.copy(
                    color = ColorPalette.NeutralBlack,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = note.content,
                style = TextStyles.textXs.copy(
                    color = ColorPalette.NeutralBlack
                ),
                maxLines = 5, // Limit content lines to prevent overly tall cards
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
