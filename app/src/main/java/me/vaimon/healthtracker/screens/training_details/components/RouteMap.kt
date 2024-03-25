package me.vaimon.healthtracker.screens.training_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import me.vaimon.healthtracker.models.RoutePoint

@Composable
fun RouteMap(
    routePoints: List<RoutePoint>,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoomPreference = 30f,
                minZoomPreference = 15f,
                mapType = MapType.TERRAIN
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
                indoorLevelPickerEnabled = false,
                zoomControlsEnabled = false,
                compassEnabled = false
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(routePoints.first().latLng, 18f)
    }

    Box(modifier.clip(shape)) {
        GoogleMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize(),
        ) {
            Polyline(
                points = routePoints.map { it.latLng },
                color = MaterialTheme.colorScheme.primary,
                startCap = RoundCap(),
                endCap = RoundCap(),
                jointType = JointType.ROUND
            )
        }
    }
}