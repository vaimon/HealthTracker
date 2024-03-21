package me.vaimon.healthtracker.util

import java.time.format.DateTimeFormatter

object Formatter {
    val labelDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM")
    val titleTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val titleDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
    fun formatDuration(seconds: Long) =
        "%02d:%02d:%02d".format(seconds / 3600, (seconds % 3600) / 60, seconds % 60)
}