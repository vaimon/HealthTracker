package me.vaimon.healthtracker.models

import com.google.maps.android.SphericalUtil
import java.time.Duration
import java.time.LocalDateTime

data class Training(
    val id: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val routePoints: List<RoutePoint>
) {
    val totalTrainingTimeSeconds = Duration.between(startTime, endTime).seconds
    val averageSpeed = routePoints.map { it.speed ?: 0f }.average().toFloat()
    val totalDistance: Float
        get() {
            return SphericalUtil.computeLength(routePoints.map {
                it.latLng
            }).toFloat() / 1000f
        }
}
