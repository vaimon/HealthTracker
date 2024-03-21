package me.vaimon.healthtracker.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.models.Training
import me.vaimon.healthtracker.models.TrainingDay
import me.vaimon.healthtracker.theme.Grey
import me.vaimon.healthtracker.theme.labelSecondary
import me.vaimon.healthtracker.util.Formatter

@Composable
fun TrainingDayStats(
    trainingDay: TrainingDay,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = pluralStringResource(
                    R.plurals.number_trainings,
                    trainingDay.trainingsCount,
                    trainingDay.trainingsCount
                ),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = Formatter.formatDuration(
                    trainingDay.totalTrainingTime
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(trainingDay.trainings) {
                TrainingCard(training = it)
            }
        }
    }
}

@Composable
fun TrainingCard(
    training: Training,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_walk),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = stringResource(R.string.training_type_walk) + training.id,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    LabelWithIcon(
                        title = training.averageSpeed.let {
                            if (it.isFinite()) stringResource(R.string.measure_km_h, it)
                            else stringResource(R.string.skipped_value)
                        },
                        iconRes = R.drawable.icon_speed,
                        tintColor = Grey
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    LabelWithIcon(
                        title = training.totalDistance.let {
                            if (it.isFinite()) stringResource(R.string.measure_km, it)
                            else stringResource(R.string.skipped_value)
                        },
                        iconRes = R.drawable.icon_distance,
                        tintColor = Grey
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = Formatter.titleTimeFormatter.format(training.startTime),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = Formatter.formatDuration(training.totalTrainingTimeSeconds),
                    style = MaterialTheme.typography.labelSecondary
                )
            }
        }
    }
}