package me.vaimon.healthtracker.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import me.vaimon.healthtracker.data.datasource.db.dao.TrainingDao
import me.vaimon.healthtracker.data.models.TrainingData
import me.vaimon.healthtracker.domain.entity.TrainingEntity
import me.vaimon.healthtracker.domain.repository.TrainingRepository
import me.vaimon.healthtracker.domain.util.Mapper
import me.vaimon.healthtracker.domain.util.Resource
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val trainingDao: TrainingDao,
    private val trainingMapper: Mapper<TrainingEntity, TrainingData>
) : TrainingRepository {

    @Suppress("USELESS_CAST")
    override fun getAllTrainings(): Flow<Resource<List<TrainingEntity>>> =
        trainingDao.getAllTrainings()
            .flowOn(Dispatchers.IO)
            .map {
                Resource.Success(it.map { trainingMapper.from(it) }) as Resource<List<TrainingEntity>>
            }.onStart {
                emit(Resource.Loading)
            }.catch {
                Log.e("HealthTracker_TrainingRepo", it.toString())
                emit(Resource.Error(it))
            }
}