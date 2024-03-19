package me.vaimon.healthtracker.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.vaimon.healthtracker.data.datasource.db.dao.RoutePointDao
import me.vaimon.healthtracker.data.datasource.db.dao.TrainingDao
import me.vaimon.healthtracker.data.models.RoutePointData
import me.vaimon.healthtracker.data.models.TrainingInfoData

@Database(
    entities = [TrainingInfoData::class, RoutePointData::class],
    exportSchema = false,
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao

    abstract fun routePointDao(): RoutePointDao

    companion object {
        const val DATABASE_NAME = "HealthTrackerDB"
    }
}