@file:OptIn(ExperimentalTime::class)

package ir.sharif.simplenote.ui.components.controls.notecard

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ir.sharif.simplenote.domain.model.Note
import ir.sharif.simplenote.ui.theme.SimpleNoteTheme
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@Preview(showBackground = true)
@Composable
fun PreviewNoteCard() {
    SimpleNoteTheme {
        NoteCard(
            note = Note(
                id = 123,
                title = "My First Note",
                content = "This is some content for my very first note. It's quite long to see how ellipsis works.",
                lastModified = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
            )
        )
    }
}
