@file:OptIn(FormatStringsInDatetimeFormats::class)

package ir.sharif.simplenote.ui.features.notemanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Trash
import ir.sharif.simplenote.ui.components.bars.taskbar.TaskBar
import ir.sharif.simplenote.ui.components.controls.freetextareainput.TitleInput
import ir.sharif.simplenote.ui.components.controls.navbar.NavBar
import ir.sharif.simplenote.ui.components.inputs.freetextareainput.FreeTextAreaInput
import ir.sharif.simplenote.ui.components.overlays.bottomsheet.ModalBottomSheet
import ir.sharif.simplenote.ui.components.overlays.dialog.SimpleDialog
import ir.sharif.simplenote.ui.theme.ColorPalette
import ir.sharif.simplenote.ui.theme.TextStyles
import kotlinx.datetime.format.FormatStringsInDatetimeFormats

/**
 * Note: For this screen to function correctly, the child composables `TitleInput`
 * and `FreeTextAreaInput` must be modified to accept `keyboardOptions` and
 * `keyboardActions` parameters and pass them to their underlying `TextField`
 * or `BasicTextField`.
 */
@Composable
fun NoteScreen(
    onNavigateBackToHome: () -> Unit = {},
    viewModel: NoteViewModel = hiltViewModel(),
) {
    val titleFocusRequester = remember { FocusRequester() }
    val contentFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        viewModel.navEvents.collect { event ->
            when (event.navEventType) {
                NavEventType.BACK -> onNavigateBackToHome()
            }
        }
    }

    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }

    val state by viewModel.uiStates.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.NeutralWhite)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )

            }
    ) {
        Scaffold(
            modifier = Modifier
                .background(ColorPalette.NeutralWhite)
                .safeDrawingPadding(),
            topBar = {
                NavBar(
                    linkText = "Back",
                    onLink = {
                        focusManager.clearFocus()
                        viewModel.saveAndBack()
                    },
                    borderBottom = true
                )
            },
            containerColor = ColorPalette.NeutralWhite,
            bottomBar = {
                TaskBar(
                    text = "Last Edited: ${state.lastModifiedTimeStamp}",
                    onButtonClick = viewModel::openDeleteConfirm,
                    showButton = state.id != null
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding).padding(top=24.dp)
                        .padding(horizontal = 16.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        focusManager.clearFocus()
                    },
            ) {
                TitleInput(
                    value = state.title,
                    onValueChange = viewModel::setTitle,
                    modifier = Modifier
                        .focusRequester(titleFocusRequester),
                    placeholder = "Title goes here ...",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            contentFocusRequester.requestFocus()
                        }
                    )
                )

                Spacer(Modifier.size(16.dp))

                FreeTextAreaInput(
                    value = state.content,
                    onValueChange = viewModel::setContent,
                    modifier = Modifier
                        .focusRequester(contentFocusRequester),
                    placeholder = "Feel Free to Write Here...",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Default
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                if (state.id != null) {
                    HorizontalDivider(modifier = Modifier.padding(top = 24.dp))
                }
            }
        }

        if (state.emptyWarnModalOpen) {
            SimpleDialog(
                dialogText = "Title or content can not empty if you leave this note will be discarded",
                dialogTitle = "Empty Note",
                onDismissRequest = viewModel::dismissEmptyNoteBack,
                dismissText = "Continue",
                onConfirmation = viewModel::onEmptyNoteBackConfirm,
                confirmText = "Discard"
            )
        }

        if (state.isDeleteModalOpen) {
            ModalBottomSheet(
                "Want to Delete this Note?",
                onDismiss = viewModel::dismissDeleteConfirm,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = viewModel::deleteNote)
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Heroicons.Outline.Trash, "delete icon",
                        tint = ColorPalette.ErrorBase
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        "Delete Note", style = TextStyles.textBaseMedium.copy(
                            color = ColorPalette.ErrorBase
                        )
                    )
                }
            }
        }
    }
}
