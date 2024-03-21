package me.vaimon.healthtracker.domain.repository

import kotlinx.coroutines.flow.Flow
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.util.Resource

interface TrainingRepository {
    fun getAllTrainings(): Flow<Resource<List<TrainingEntity>>>

    fun getTrainingsByPeriod(
        startDateTimestamp: Long,
        endDateTimestamp: Long
    ): Flow<Resource<List<TrainingEntity>>>

    suspend fun getTrainingById(id: Int): TrainingEntity
}