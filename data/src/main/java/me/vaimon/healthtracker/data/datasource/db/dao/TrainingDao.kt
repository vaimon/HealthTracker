package me.vaimon.healthtracker.data.datasource.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.vaimon.healthtracker.data.models.TrainingData
import me.vaimon.healthtracker.data.models.TrainingInfoData

@Dao
interface TrainingDao {

    @Insert
    fun createTraining(training: TrainingInfoData)

    @Update
    fun updateTraining(training: TrainingInfoData)

    @Transaction
    @Query("SELECT * FROM trainings")
    fun getAllTrainings(): Flow<List<TrainingData>>

    @Transaction
    @Query("SELECT * FROM trainings WHERE startTime BETWEEN :start AND :end")
    fun getTrainingsInPeriod(start: Long, end: Long): Flow<List<TrainingData>>

    @Query("SELECT * FROM trainings WHERE id = :id")
    suspend fun getTrainingById(id: Int): TrainingData
}