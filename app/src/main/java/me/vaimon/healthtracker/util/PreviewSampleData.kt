package me.vaimon.healthtracker.util

import me.vaimon.healthtracker.domain.util.Resource
import me.vaimon.healthtracker.models.Training
import java.time.LocalDate
import java.time.LocalDateTime

object PreviewSampleData {
    val trainings = Resource.Success(
        mapOf(
            LocalDate.of(2024, 3, 19) to listOf(
                Training(1, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(2, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(3, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(4, LocalDateTime.now(), LocalDateTime.now(), listOf())
            ),
            LocalDate.of(2024, 3, 18) to listOf(),
            LocalDate.of(2024, 3, 17) to listOf(
                Training(5, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 16) to listOf(
                Training(6, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(7, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(8, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(9, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(10, LocalDateTime.now(), LocalDateTime.now(), listOf()),

                ),
            LocalDate.of(2024, 3, 15) to listOf(
                Training(11, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(12, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 14) to listOf(
                Training(13, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(14, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(15, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(16, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(17, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(18, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 13) to listOf(
                Training(19, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(20, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(21, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(22, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 12) to listOf(
                Training(23, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 11) to listOf(
                Training(24, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(25, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 10) to listOf(
                Training(26, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(27, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(28, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 9) to listOf(),
            LocalDate.of(2024, 3, 8) to listOf(
                Training(26, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 7) to listOf(
                Training(26, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(27, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(28, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(27, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(28, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
            LocalDate.of(2024, 3, 6) to listOf(
                Training(26, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(27, LocalDateTime.now(), LocalDateTime.now(), listOf()),
                Training(28, LocalDateTime.now(), LocalDateTime.now(), listOf()),
            ),
        )
    )
}