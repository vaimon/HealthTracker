package me.vaimon.healthtracker.models

data class RoutePoint(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val speed: Float?,
    val timestamp: Long,
)