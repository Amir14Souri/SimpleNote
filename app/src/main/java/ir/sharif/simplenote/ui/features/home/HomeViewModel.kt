@file:OptIn(ExperimentalCoroutinesApi::class)

package ir.sharif.simplenote.ui.features.home

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.domain.model.Note
import ir.sharif.simplenote.domain.model.toNote
import ir.sharif.simplenote.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class HomeUiState(val searchQueryTextField: TextFieldValue, val searchQuery: String? = null)
@HiltViewModel
class HomeViewModel @Inject constructor(noteRepository: NoteRepository) : ViewModel(){

    val _uiState = MutableStateFlow(HomeUiState(TextFieldValue("")))
    val uiState = _uiState.asStateFlow()
    val notes: Flow<PagingData<Note>> = uiState
        .map { it.searchQuery }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query == null) {
                noteRepository.getNotesPaged()
            } else {
                noteRepository.searchNotesPaged(query)
            }
        }
        .map { pagingData ->
            pagingData.map { it.toNote() }
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChange(value: TextFieldValue) {
        _uiState.value = _uiState.value.copy(searchQueryTextField = value)
    }

    fun onSearch() {
        if (!_uiState.value.searchQueryTextField.text.isEmpty()) {
            _uiState.value =
                _uiState.value.copy(searchQuery = _uiState.value.searchQueryTextField.text)
        }
    }

    fun onClearSearchClick() {
        _uiState.value = _uiState.value.copy(searchQuery = null, searchQueryTextField = TextFieldValue(""))
    }
}