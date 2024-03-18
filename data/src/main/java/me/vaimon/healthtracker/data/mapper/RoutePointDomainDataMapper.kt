package me.vaimon.healthtracker.data.mapper

import me.vaimon.healthtracker.data.models.RoutePointData
import me.vaimon.healthtracker.domain.entity.RoutePointEntity
import javax.inject.Inject

class RoutePointDomainDataMapper @Inject constructor() : Mapper<RoutePointEntity, RoutePointData> {
    override fun from(e: RoutePointData): RoutePointEntity {
        return RoutePointEntity(
            id = e.id,
            latitude = e.latitude,
            longitude = e.longitude,
            speed = e.speed,
            timestamp = e.timestamp
        )
    }

    override fun to(t: RoutePointEntity): RoutePointData {
        throw IllegalStateException()
    }
}