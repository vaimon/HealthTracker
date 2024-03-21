package me.vaimon.healthtracker.domain.usecase

import kotlinx.coroutines.flow.map
import me.vaimon.healthtracker.domain.repository.TrainingRepository
import me.vaimon.healthtracker.domain.util.getDaysUntil
import me.vaimon.healthtracker.domain.util.map
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GetAggregatedTrainingsPeriodUseCase @Inject constructor(
    private val trainingRepository: TrainingRepository
) {
    operator fun invoke(
        startDate: LocalDate = LocalDate.now().minusMonths(1),
        endDate: LocalDate = LocalDate.now()
    ) = trainingRepository.getTrainingsByPeriod(
        startDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
        endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
    ).map { resource ->
        resource.map { trainingList ->
            trainingList.filter {
                it.endTime != null
            }.groupBy {
                it.startTime.toLocalDate()
            }.toMutableMap().let { map ->
                startDate.getDaysUntil(endDate).forEach {
                    if (!map.containsKey(it))
                        map[it] = listOf()
                }
                map
            }.toSortedMap()
        }
    }
}