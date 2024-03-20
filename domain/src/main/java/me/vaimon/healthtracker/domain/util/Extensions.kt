package me.vaimon.healthtracker.domain.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.getDaysUntil(until: LocalDate): List<LocalDate> =
    (0L..ChronoUnit.DAYS.between(this, until)).map {
        this.plusDays(it)
    }