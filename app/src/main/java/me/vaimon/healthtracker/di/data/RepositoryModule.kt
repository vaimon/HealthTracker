package me.vaimon.healthtracker.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vaimon.healthtracker.data.repository.TrainingRepositoryImpl
import me.vaimon.healthtracker.domain.repository.TrainingRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindTrainingRepository(
        repo: TrainingRepositoryImpl
    ): TrainingRepository
}