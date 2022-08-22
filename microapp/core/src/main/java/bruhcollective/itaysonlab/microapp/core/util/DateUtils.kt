package bruhcollective.itaysonlab.microapp.core.util

import kotlinx.datetime.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateUtils {
    fun formatDateToLocale(iso: String): String {
        val dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        val date = Instant.parse(iso).toLocalDateTime(TimeZone.UTC).date
        return dtf.format(date.toJavaLocalDate())
    }

    fun formatDateTimeToLocale(iso: String): String {
        val dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val date = Instant.parse(iso).toLocalDateTime(TimeZone.UTC)
        return dtf.format(date.toJavaLocalDateTime())
    }

    fun formatRelative(iso: String): String {
        return android.text.format.DateUtils.getRelativeTimeSpanString(
            Instant.parse(iso).toEpochMilliseconds(),
            System.currentTimeMillis(),
            0L,
            android.text.format.DateUtils.FORMAT_ABBREV_ALL
        ).toString()
    }
}