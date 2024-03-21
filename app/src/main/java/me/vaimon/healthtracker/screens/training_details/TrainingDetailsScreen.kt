package me.vaimon.healthtracker.screens.training_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.models.Training
import me.vaimon.healthtracker.navigation.NavigationDestinationWithArg
import me.vaimon.healthtracker.screens.components.ResourceLoading
import me.vaimon.healthtracker.theme.Grey
import me.vaimon.healthtracker.theme.HealthTrackerTheme
import me.vaimon.healthtracker.theme.labelSecondary
import me.vaimon.healthtracker.theme.titleMediumSmall
import me.vaimon.healthtracker.util.Formatter
import me.vaimon.healthtracker.util.PreviewSampleData
import me.vaimon.healthtracker.util.floatStringResource

object TrainingDetailsDestination : NavigationDestinationWithArg<Int>() {
    override val routeBase = "trainingDetails"
    override val argName = "trainingId"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TrainingDetailsViewModel = hiltViewModel()
) {
    val training by viewModel.trainingState.collectAsState()

    Scaffold(
        topBar = {
            TrainingDetailsAppBar(
                navigateUp = { navController.navigateUp() },
                training = training
            )
        },
        modifier = modifier
    ) { padding ->
        training?.also {
            TrainingDetailsBody(
                training = training!!,
                modifier = modifier.padding(padding)
            )
        } ?: run {
            ResourceLoading()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDetailsAppBar(
    navigateUp: () -> Unit,
    training: Training?,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row {
                Text(
                    stringResource(id = R.string.training_type_walk),
                    style = MaterialTheme.typography.titleMediumSmall,
                    modifier = Modifier.alignByBaseline()
                )
                training?.startTime?.let {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        Formatter.titleDateTimeFormatter.format(it),
                        style = MaterialTheme.typography.labelSmall,
                        color = Grey,
                        modifier = Modifier.alignByBaseline()
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    painterResource(id = R.drawable.icon_back),
                    contentDescription = stringResource(R.string.desc_back),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun TrainingDetailsBody(
    training: Training,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TrainingStatsCard(
            training = training,
            modifier = Modifier.padding(16.dp)
        )

        RouteMap(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .aspectRatio(1f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun RouteMap(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    Box(modifier.clip(shape)) {

    }
}

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

@Composable
fun LabeledStatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSecondary
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
fun PreviewAppBar() {
    HealthTrackerTheme {
        TrainingDetailsAppBar(
            navigateUp = { },
            training = PreviewSampleData.trainings.data.values.first().trainings.first()
        )
    }
}

@Preview
@Composable
fun PreviewDetailsBody() {
    HealthTrackerTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            TrainingDetailsBody(
                training = PreviewSampleData.trainings.data.values.first().trainings.first()
            )
        }
    }
}