package me.vaimon.healthtracker.data.datasource.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import me.vaimon.healthtracker.data.models.RoutePointData

@Dao
interface RoutePointDao {

    @Insert
    fun insertRoutePoint(point: RoutePointData)

    @Delete
    fun deleteRoutePoint(point: RoutePointData)

}