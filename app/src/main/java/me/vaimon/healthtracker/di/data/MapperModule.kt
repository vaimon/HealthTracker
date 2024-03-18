package me.vaimon.healthtracker.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vaimon.healthtracker.data.mapper.Mapper
import me.vaimon.healthtracker.data.mapper.RoutePointDomainDataMapper
import me.vaimon.healthtracker.data.mapper.TrainingDomainDataMapper
import me.vaimon.healthtracker.data.models.RoutePointData
import me.vaimon.healthtracker.data.models.TrainingData
import me.vaimon.healthtracker.domain.entity.RoutePointEntity
import me.vaimon.healthtracker.domain.entity.TrainingEntity

@Module
@InstallIn(SingletonComponent::class)
interface MapperModule {

    @Binds
    fun bindTrainingMapper(
        mapper: TrainingDomainDataMapper
    ): Mapper<TrainingEntity, TrainingData>

    @Binds
    fun bindRoutePointMapper(
        mapper: RoutePointDomainDataMapper
    ): Mapper<RoutePointEntity, RoutePointData>

}