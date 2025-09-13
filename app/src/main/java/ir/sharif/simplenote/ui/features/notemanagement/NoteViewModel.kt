@file:OptIn(ExperimentalTime::class)

package ir.sharif.simplenote.ui.features.notemanagement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.domain.repository.NoteRepository
import ir.sharif.simplenote.util.formatHumanReadableTimestamp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class NoteManagementUIState(
    val id: Long? = null,
    val title: String = "",
    val content: String = "",
    val lastModifiedTimeStamp: String = "New Note",

    val isDeleteModalOpen: Boolean = false,
    val emptyWarnModalOpen: Boolean = false
)

enum class NavEventType{
    BACK,
}

data class NavEvent(val navEventType: NavEventType)

@HiltViewModel
class NoteViewModel @Inject constructor(
    private  val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventChannel = Channel<NavEvent>()
    val navEvents = _eventChannel.receiveAsFlow()

    private val _uiStates = MutableStateFlow(NoteManagementUIState())
    val uiStates = _uiStates.asStateFlow()

    init {
        val noteId: Long? = savedStateHandle.get<Long>("noteId")
        if (noteId != null) {
            viewModelScope.launch {
                val note = noteRepository.getNoteById(noteId)
                if (note != null) {
                    _uiStates.update { currentState ->
                        currentState.copy(
                            id = note.id,
                            title = note.title,
                            content = note.content,
                            lastModifiedTimeStamp = formatHumanReadableTimestamp(note.lastModified),
                        )
                    }
                }
            }
        }
    }


    fun setTitle(title: String) {
        _uiStates.update { currentState ->
            currentState.copy(title = title)
        }
    }

    fun setContent(content: String) {
        _uiStates.update { currentState ->
            currentState.copy(content = content)
        }
    }

    fun saveAndBack() {
        val currentState = _uiStates.value
        viewModelScope.launch {
            if (currentState.title.isEmpty() || currentState.content.isEmpty()) {
                _uiStates.value = _uiStates.value.copy(emptyWarnModalOpen = true)
                return@launch
            }

            if (currentState.id != null) {
                noteRepository.updateNoteById(
                    currentState.id,
                    currentState.title,
                    currentState.content
                )
                _eventChannel.send(NavEvent(NavEventType.BACK))
            } else {
                noteRepository.createNote(
                    currentState.title,
                    currentState.content
                )

                _eventChannel.send(NavEvent(NavEventType.BACK))
            }
        }
    }

    fun deleteNote() {
        val currentState = _uiStates.value
        viewModelScope.launch {
            if (currentState.id != null && currentState.isDeleteModalOpen) {
                noteRepository.deleteNoteById(currentState.id).onSuccess {
                    _uiStates.value = _uiStates.value.copy(isDeleteModalOpen = false)
                    _eventChannel.send(NavEvent(NavEventType.BACK))
                }
            }
        }
    }

    fun openDeleteConfirm() {
        _uiStates.value = _uiStates.value.copy(isDeleteModalOpen = true)
    }

    fun dismissDeleteConfirm() {
        _uiStates.value = _uiStates.value.copy(isDeleteModalOpen = false)
    }

    fun dismissEmptyNoteBack() {
        _uiStates.value = _uiStates.value.copy(emptyWarnModalOpen = false)
    }

    fun onEmptyNoteBackConfirm() {
        _uiStates.value = _uiStates.value.copy(emptyWarnModalOpen = false)
        viewModelScope.launch {
            _eventChannel.send(NavEvent(NavEventType.BACK))
        }
    }
}
