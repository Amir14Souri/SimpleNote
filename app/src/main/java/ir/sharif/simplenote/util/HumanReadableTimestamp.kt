package ir.sharif.simplenote.util
import kotlinx.datetime.*
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toJavaLocalDateTime // Important import for conversion
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun formatHumanReadableTimestamp(
    lastModified: LocalDateTime,
    clock: Clock = Clock.System
): String {
    val now: Instant = clock.now()
    val systemTimeZone: TimeZone = TimeZone.currentSystemDefault()
    val nowInSystemZone: LocalDateTime = now.toLocalDateTime(systemTimeZone)

    val userLocale = Locale.getDefault()


    val timeFormat = LocalDateTime.Format { byUnicodePattern("HH:mm") }

    val javaLastModified = lastModified.toJavaLocalDateTime()

    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", userLocale)
    val monthDayFormatter = DateTimeFormatter.ofPattern("MMMM d", userLocale)
    val fullDateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", userLocale)


    if (lastModified.date == nowInSystemZone.date) {
        return "Today at ${lastModified.format(timeFormat)}"
    }

    val yesterdayDate = now.minus(1, DateTimeUnit.DAY, systemTimeZone).toLocalDateTime(systemTimeZone).date
    if (lastModified.date == yesterdayDate) {
        return "Yesterday at ${lastModified.format(timeFormat)}"
    }

    val daysUntilNow = lastModified.date.daysUntil(nowInSystemZone.date)

    if (daysUntilNow < 7) {
        val dayOfWeek = javaLastModified.format(dayOfWeekFormatter)
        val time = lastModified.format(timeFormat)
        return "$dayOfWeek at $time"
    }

    if (lastModified.year == nowInSystemZone.year) {
        return javaLastModified.format(monthDayFormatter)
    }

    return javaLastModified.format(fullDateFormatter)
}