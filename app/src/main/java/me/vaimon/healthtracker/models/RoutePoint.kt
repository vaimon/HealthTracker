package me.vaimon.healthtracker.models

import com.google.android.gms.maps.model.LatLng

data class RoutePoint(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val speed: Float?,
    val timestamp: Long,
) {
    val latLng: LatLng
        get() = LatLng(latitude, longitude)
}