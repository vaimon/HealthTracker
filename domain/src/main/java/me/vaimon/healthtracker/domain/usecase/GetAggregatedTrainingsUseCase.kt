package me.vaimon.healthtracker.domain.usecase

import kotlinx.coroutines.flow.map
import me.vaimon.healthtracker.domain.repository.TrainingRepository
import me.vaimon.healthtracker.domain.util.map
import javax.inject.Inject

class GetAggregatedTrainingsUseCase @Inject constructor(
    private val trainingRepository: TrainingRepository
) {
    operator fun invoke() =
        trainingRepository.getAllTrainings().map { res ->
            res.map { trainingList ->
                trainingList.groupBy {
                    it.startTime.toLocalDate()
                }
            }
        }
}