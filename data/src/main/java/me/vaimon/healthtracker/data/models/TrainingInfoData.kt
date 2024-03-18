package me.vaimon.healthtracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "trainings")
data class TrainingInfoData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
)
