package ir.sharif.simplenote.domain.model

import ir.sharif.simplenote.database.Notes
import kotlinx.datetime.LocalDateTime

data class Note(val id: Long, val title: String, val content: String, val lastModified: LocalDateTime)

fun Notes.toNote() = Note(
    id = id,
    title = title,
    content = content,
    lastModified = updated_at,
)