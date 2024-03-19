package me.vaimon.healthtracker.domain.entity


data class TrainingEntity(
    val id: Int,
    val startTime: Long?,
    val endTime: Long?,
    val routePoints: List<RoutePointEntity>
)
