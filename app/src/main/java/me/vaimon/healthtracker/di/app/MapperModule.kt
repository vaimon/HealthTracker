package me.vaimon.healthtracker.di.app

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vaimon.healthtracker.domain.entity.RoutePointEntity
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.util.Mapper
import me.vaimon.healthtracker.mapper.RoutePointAppDomainMapper
import me.vaimon.healthtracker.mapper.TrainingAppDomainMapper
import me.vaimon.healthtracker.models.RoutePoint
import me.vaimon.healthtracker.models.Training

@Module
@InstallIn(SingletonComponent::class)
interface MapperModule {

    @Binds
    fun bindTrainingMapper(
        mapper: TrainingAppDomainMapper
    ): Mapper<Training, TrainingEntity>

    @Binds
    fun bindRoutePointMapper(
        mapper: RoutePointAppDomainMapper
    ): Mapper<RoutePoint, RoutePointEntity>
}