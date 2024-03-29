package me.vaimon.healthtracker.di.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.vaimon.healthtracker.data.datasource.db.AppDatabase
import me.vaimon.healthtracker.data.datasource.db.dao.RoutePointDao
import me.vaimon.healthtracker.data.datasource.db.dao.TrainingDao
import me.vaimon.healthtracker.data.datasource.db.migrations.Migration1to2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        )
            .createFromAsset("db/db_seed.db")
            .addMigrations(Migration1to2)
            .build()
    }

    @Singleton
    @Provides
    fun provideTrainingDao(db: AppDatabase): TrainingDao {
        return db.trainingDao()
    }

    @Singleton
    @Provides
    fun provideRoutePointDao(db: AppDatabase): RoutePointDao {
        return db.routePointDao()
    }
}