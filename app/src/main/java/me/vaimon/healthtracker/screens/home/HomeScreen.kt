package me.vaimon.healthtracker.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.domain.util.Resource
import me.vaimon.healthtracker.models.TrainingDay
import me.vaimon.healthtracker.navigation.NavigationDestination
import me.vaimon.healthtracker.screens.components.ResourceLoading
import me.vaimon.healthtracker.screens.home.components.ActivityCalendar
import me.vaimon.healthtracker.screens.home.components.LargeActionButton
import me.vaimon.healthtracker.screens.home.components.TextStub
import me.vaimon.healthtracker.screens.training_details.TrainingDetailsDestination
import me.vaimon.healthtracker.theme.HealthTrackerTheme
import me.vaimon.healthtracker.theme.titleMediumSmall
import me.vaimon.healthtracker.util.ExceptionTranslator
import me.vaimon.healthtracker.util.PreviewSampleData
import java.time.LocalDate

object HomeScreenDestination : NavigationDestination {
    override val route = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {
    val trainingList = viewModel.trainings.collectAsState(initial = Resource.Loading)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMediumSmall,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) {
        Column(
            modifier.padding(it)
        ) {
            HomeBody(
                trainings = trainingList.value,
                navigateToTrainingDetails = {
                    navController.navigate(TrainingDetailsDestination.getDestinationWithArg(it))
                }
            )
        }
    }
}

@Composable
fun HomeBody(
    trainings: Resource<Map<LocalDate, TrainingDay>>,
    navigateToTrainingDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val innerPaddingModifier = Modifier.padding(PaddingValues(horizontal = 16.dp))
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        LargeActionButton(
            icon = R.drawable.icon_walk,
            text = stringResource(R.string.action_start_walk),
            onClick = {},
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.header_last_activity),
                style = MaterialTheme.typography.titleLarge,
                modifier = innerPaddingModifier
            )
            when (trainings) {
                is Resource.Error -> TextStub(message = ExceptionTranslator.translate(trainings.exception))
                is Resource.Loading -> ResourceLoading()
                is Resource.Success -> ActivityCalendar(
                    activityData = trainings.data,
                    navigateToTrainingDetails = navigateToTrainingDetails
                )
            }
        }

    }
}

@Preview
@Composable
fun HomePreviewEmptyList() {
    HealthTrackerTheme {
        Scaffold {
            HomeBody(
                Resource.Success(emptyMap()),
                {},
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HealthTrackerTheme {
        Scaffold {
            HomeBody(
                trainings = PreviewSampleData.trainings,
                {},
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun HomePreviewError() {
    HealthTrackerTheme {
        Scaffold {
            HomeBody(
                Resource.Error(IllegalStateException()),
                {},
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun HomePreviewLoading() {
    HealthTrackerTheme {
        Scaffold {
            HomeBody(
                Resource.Loading,
                {},
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}