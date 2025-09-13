package ir.sharif.simplenote.ui.features.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.data.local.SessionManager
import ir.sharif.simplenote.domain.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(sessionManager: SessionManager,
                                          private val noteRepository: NoteRepository): ViewModel() {
    val authStatus = sessionManager.authStatus

    fun registerWorkers() {
        noteRepository.scheduleImmediateSync()
        noteRepository.schedulePeriodicSync()
    }

}