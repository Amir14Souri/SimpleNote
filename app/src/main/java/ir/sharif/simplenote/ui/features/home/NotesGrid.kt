package ir.sharif.simplenote.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ir.sharif.simplenote.domain.model.Note
import ir.sharif.simplenote.ui.components.controls.notecard.NoteCard
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles

@Composable
fun NotesGrid(
    notes: LazyPagingItems<Note>,
    onNoteClick: (Note) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key="title", span = { GridItemSpan(2)}) {
            Spacer(Modifier.size(12.dp))
            Text("Notes", style = TextStyles.textSmBold.copy(color= ColorPalette.NeutralBlack), textAlign = TextAlign.Center)
            Spacer(Modifier.size(12.dp))
        }
        items(count = notes.itemCount,  key = { index -> notes[index]?.id ?: index }) { index ->
            val note = notes[index]?: return@items
            NoteCard(
                note = note,
                onClick = {onNoteClick(note)}
            )
        }
    }
}
