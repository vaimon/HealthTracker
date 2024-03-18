package me.vaimon.healthtracker.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingData(
    @Embedded val trainingInfo: TrainingInfoData,
    @Relation(
        parentColumn = "id",
        entityColumn = "trainingId"
    )
    val routePoints: List<RoutePointData>
)