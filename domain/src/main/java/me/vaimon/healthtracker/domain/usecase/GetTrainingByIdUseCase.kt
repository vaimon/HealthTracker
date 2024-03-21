package me.vaimon.healthtracker.domain.usecase

import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.repository.TrainingRepository
import javax.inject.Inject

class GetTrainingByIdUseCase @Inject constructor(
    private val trainingRepository: TrainingRepository
) {
    suspend operator fun invoke(id: Int): TrainingEntity = trainingRepository.getTrainingById(id)
}