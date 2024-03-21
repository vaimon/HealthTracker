package me.vaimon.healthtracker.screens.training_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.usecase.GetTrainingByIdUseCase
import me.vaimon.healthtracker.domain.util.Mapper
import me.vaimon.healthtracker.models.Training
import javax.inject.Inject

@HiltViewModel
class TrainingDetailsViewModel @Inject constructor(
    private val trainingMapper: Mapper<Training, TrainingEntity>,
    savedStateHandle: SavedStateHandle,
    getTrainingByIdUseCase: GetTrainingByIdUseCase
) : ViewModel() {
    private val trainingId =
        checkNotNull(savedStateHandle.get<Int>(TrainingDetailsDestination.argName))

    val trainingState = flow {
        emit(trainingMapper.from(getTrainingByIdUseCase(trainingId)))
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

}