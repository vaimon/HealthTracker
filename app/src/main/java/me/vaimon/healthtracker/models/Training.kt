package me.vaimon.healthtracker.models

import java.time.Duration
import java.time.LocalDateTime

data class Training(
    val id: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val routePoints: List<RoutePoint>
) {
    val totalTrainingTimeSeconds = Duration.between(startTime, endTime).seconds
    val averageSpeed = routePoints.map { it.speed ?: 0f }.average()
    val totalDistance: Float
        get() {
            return Float.NaN
        }
}
