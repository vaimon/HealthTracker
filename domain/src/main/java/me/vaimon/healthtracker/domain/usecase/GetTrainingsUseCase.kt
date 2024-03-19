package me.vaimon.healthtracker.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.repository.TrainingRepository
import me.vaimon.healthtracker.domain.util.Resource
import javax.inject.Inject

class GetTrainingsUseCase @Inject constructor(
    private val trainingRepository: TrainingRepository
) {
    operator fun invoke(): Flow<Resource<List<TrainingEntity>>> =
        trainingRepository.getAllTrainings()
}