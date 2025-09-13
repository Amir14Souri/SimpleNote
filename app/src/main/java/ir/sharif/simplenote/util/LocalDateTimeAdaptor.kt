package ir.sharif.simplenote.util

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

object LocalDateTimeAdapter: ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String): LocalDateTime {
        return LocalDateTime.parse(databaseValue, format = LocalDateTime.Formats.ISO)
    }

    override fun encode(value: LocalDateTime): String {
        return value.format(LocalDateTime.Formats.ISO)
    }
}
