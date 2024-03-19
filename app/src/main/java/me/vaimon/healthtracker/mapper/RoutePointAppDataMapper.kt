package me.vaimon.healthtracker.mapper

import me.vaimon.healthtracker.domain.entity.RoutePointEntity
import me.vaimon.healthtracker.domain.util.Mapper
import me.vaimon.healthtracker.models.RoutePoint
import javax.inject.Inject

class RoutePointAppDomainMapper @Inject constructor() : Mapper<RoutePoint, RoutePointEntity> {
    override fun from(e: RoutePointEntity): RoutePoint {
        return RoutePoint(
            e.id,
            e.latitude,
            e.longitude,
            e.speed,
            e.timestamp
        )
    }

    override fun to(t: RoutePoint): RoutePointEntity {
        throw IllegalStateException()
    }
}