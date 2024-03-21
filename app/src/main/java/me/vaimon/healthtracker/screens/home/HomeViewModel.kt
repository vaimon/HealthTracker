package me.vaimon.healthtracker.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.usecase.GetAggregatedTrainingsPeriodUseCase
import me.vaimon.healthtracker.domain.util.Mapper
import me.vaimon.healthtracker.domain.util.map
import me.vaimon.healthtracker.models.Training
import me.vaimon.healthtracker.models.TrainingDay
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAggregatedTrainingsPeriodUseCase: GetAggregatedTrainingsPeriodUseCase,
    private val trainingMapper: Mapper<Training, TrainingEntity>
) : ViewModel() {
    val trainings = getAggregatedTrainingsPeriodUseCase().map { resource ->
        resource.map { aggregatedMap ->
            aggregatedMap.mapValues { entry ->
                TrainingDay(
                    entry.value.map { trainingMapper.from(it) }
                )
            }
        }
    }

}