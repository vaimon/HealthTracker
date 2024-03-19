package me.vaimon.healthtracker.models

import java.time.LocalDateTime

data class Training(
    val id: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val routePoints: List<RoutePoint>
)
