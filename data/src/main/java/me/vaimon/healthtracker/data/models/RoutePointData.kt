package me.vaimon.healthtracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routePoints")
data class RoutePointData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val trainingId: Int,
    val latitude: Double,
    val longitude: Double,
    val speed: Float?,
    val timestamp: Long,
)