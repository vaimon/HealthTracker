package me.vaimon.healthtracker.data.datasource.db.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import me.vaimon.healthtracker.data.models.TrainingData
import me.vaimon.healthtracker.data.models.TrainingInfoData

interface TrainingDao {

    @Insert
    fun createTraining(training: TrainingInfoData)

    @Update
    fun updateTraining(training: TrainingInfoData)

    @Transaction
    @Query("SELECT * FROM trainings")
    fun getUsersWithPlaylists(): List<TrainingData>
}