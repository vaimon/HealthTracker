package me.vaimon.healthtracker.domain.entity

data class RoutePointEntity(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val speed: Float?,
    val timestamp: Long,
)