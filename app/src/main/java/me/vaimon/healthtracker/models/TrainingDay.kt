package me.vaimon.healthtracker.models

class TrainingDay(
    val trainings: List<Training>
) {
    val totalTrainingTime = trainings.fold(0L) { acc, training ->
        acc + training.totalTrainingTimeSeconds
    }
    val trainingsCount = trainings.size
}