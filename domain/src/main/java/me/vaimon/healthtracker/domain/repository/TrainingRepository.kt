package me.vaimon.healthtracker.domain.repository

import me.vaimon.healthtracker.domain.entity.TrainingEntity

interface TrainingRepository {
    fun getAllTrainings(): List<TrainingEntity>
}