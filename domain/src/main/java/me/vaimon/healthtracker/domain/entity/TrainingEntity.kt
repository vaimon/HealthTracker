package me.vaimon.healthtracker.domain.entity

import java.time.LocalDateTime


data class TrainingEntity(
    val id: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val routePoints: List<RoutePointEntity>
)
