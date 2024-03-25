package me.vaimon.healthtracker.screens.training_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.models.Training
import me.vaimon.healthtracker.util.Formatter
import me.vaimon.healthtracker.util.floatStringResource

@Composable
fun TrainingStatsCard(
    training: Training,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    Card(
        shape = shape,
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                LabeledStatItem(
                    label = stringResource(R.string.label_avg_speed),
                    value = floatStringResource(R.string.measure_km_h, training.averageSpeed)
                )
            }
            item {
                LabeledStatItem(
                    label = stringResource(R.string.label_distance),
                    value = floatStringResource(R.string.measure_km_h, training.totalDistance)
                )
            }
            item {
                LabeledStatItem(
                    label = stringResource(R.string.label_total_time),
                    value = Formatter.formatDuration(training.totalTrainingTimeSeconds)
                )
            }
        }
    }
}