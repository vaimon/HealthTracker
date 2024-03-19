package me.vaimon.healthtracker.mapper

import me.vaimon.healthtracker.domain.entity.RoutePointEntity
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.util.Mapper
import me.vaimon.healthtracker.models.RoutePoint
import me.vaimon.healthtracker.models.Training
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class TrainingAppDomainMapper @Inject constructor(
    private val routePointMapper: Mapper<RoutePoint, RoutePointEntity>
) : Mapper<Training, TrainingEntity> {
    override fun from(e: TrainingEntity): Training {
        return Training(
            e.id,
            e.startTime?.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
            },
            e.endTime?.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
            },
            e.routePoints.map {
                routePointMapper.from(it)
            }
        )
    }

    override fun to(t: Training): TrainingEntity {
        throw IllegalStateException()
    }
}