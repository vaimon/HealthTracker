package me.vaimon.healthtracker.data.mapper

import me.vaimon.healthtracker.data.models.RoutePointData
import me.vaimon.healthtracker.data.models.TrainingData
import me.vaimon.healthtracker.domain.entity.RoutePointEntity
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import javax.inject.Inject

class TrainingDomainDataMapper @Inject constructor(
    private val routePointMapper: Mapper<RoutePointEntity, RoutePointData>
) : Mapper<TrainingEntity, TrainingData> {
    override fun from(e: TrainingData): TrainingEntity {
        return TrainingEntity(
            id = e.trainingInfo.id,
            startTime = e.trainingInfo.startTime,
            endTime = e.trainingInfo.endTime,
            routePoints = e.routePoints.map { routePointMapper.from(it) }
        )
    }

    override fun to(t: TrainingEntity): TrainingData {
        throw IllegalStateException()
    }
}