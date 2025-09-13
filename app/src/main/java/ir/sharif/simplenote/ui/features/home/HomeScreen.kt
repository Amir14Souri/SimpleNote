package ir.sharif.simplenote.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.XMark
import ir.sharif.simplenote.ui.components.bars.searchbar.SearchBar
import ir.sharif.simplenote.ui.components.bars.tabbar.TabBar
import ir.sharif.simplenote.ui.theme.ColorPalette


@Composable
fun HomeScreen(
    onNavigateToCreateNote: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNoteClick: (noteId: Long) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val notes = viewModel.notes.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize().clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                viewModel.onSearch()
                focusManager.clearFocus()
            },
        ),
        bottomBar = {
            TabBar(
                onNavigateToCreateNote,
                onNavigateToSettings,
                modifier = Modifier
            )
        },
        containerColor = ColorPalette.PrimaryBackground,
    ) { innerPadding ->
            if (notes.itemCount == 0 && notes.loadState.refresh is LoadState.NotLoading) {
                if (state.searchQuery == null) {
                    EmptyNotesContent(Modifier.padding(innerPadding))
                } else {
                    NoResultsFound(
                        Modifier.padding(innerPadding),
                        state.searchQuery!!,
                    )
                }
            }

            Column(modifier=Modifier.padding(innerPadding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                SearchBar(query =  state.searchQueryTextField,
                    onQueryChange = viewModel::onSearchQueryChange,
                    onIconClick = viewModel::onSearch,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars.
                    only(WindowInsetsSides.Top)),
                    textFieldIcon = Heroicons.Outline.XMark,
                    onTextFieldIconClick = {
                        viewModel.onClearSearchClick()
                        focusManager.clearFocus()
                    }
                )
                NotesGrid(
                    notes = notes,
                    onNoteClick = { note -> onNoteClick(note.id)},
                )
            }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        onNavigateToCreateNote = {},
        onNoteClick = {}
    )
}