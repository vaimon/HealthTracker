package me.vaimon.healthtracker.screens.training_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.models.Training
import me.vaimon.healthtracker.navigation.NavigationDestinationWithArg
import me.vaimon.healthtracker.screens.components.ResourceLoading
import me.vaimon.healthtracker.screens.training_details.components.RouteMap
import me.vaimon.healthtracker.screens.training_details.components.TrainingStatsCard
import me.vaimon.healthtracker.theme.Grey
import me.vaimon.healthtracker.theme.HealthTrackerTheme
import me.vaimon.healthtracker.theme.titleMediumSmall
import me.vaimon.healthtracker.util.Formatter
import me.vaimon.healthtracker.util.PreviewSampleData

object TrainingDetailsDestination : NavigationDestinationWithArg<Int>() {
    override val routeBase = "trainingDetails"
    override val argName = "trainingId"
}

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
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
    ) {
        TrainingStatsCard(
            training = training,
            modifier = Modifier.padding(16.dp)
        )

        if (training.routePoints.isNotEmpty()) {
            RouteMap(
                routePoints = training.routePoints,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
        }
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