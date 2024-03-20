package me.vaimon.healthtracker.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.domain.util.Resource
import me.vaimon.healthtracker.models.Training
import me.vaimon.healthtracker.navigation.NavigationDestination
import me.vaimon.healthtracker.screens.home.components.ActivityCalendarRow
import me.vaimon.healthtracker.screens.home.components.LabelWithIcon
import me.vaimon.healthtracker.screens.home.components.LargeActionButton
import me.vaimon.healthtracker.screens.home.components.ResourceLoading
import me.vaimon.healthtracker.screens.home.components.TextStub
import me.vaimon.healthtracker.theme.Grey
import me.vaimon.healthtracker.theme.HealthTrackerTheme
import me.vaimon.healthtracker.theme.labelSecondary
import me.vaimon.healthtracker.util.ExceptionTranslator
import me.vaimon.healthtracker.util.Formatter
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
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
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
                trainingList.value
            )
        }
    }
}

@Composable
fun HomeBody(
    trainings:
    Resource<Map<LocalDate, List<Training>>>,
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
                is Resource.Error -> TextStub(errorMessage = ExceptionTranslator.translate(trainings.exception))
                is Resource.Loading -> ResourceLoading()
                is Resource.Success -> ActivityCalendar(activityData = trainings.data)
            }
        }

    }
}

@Composable
fun ActivityCalendar(
    activityData: Map<LocalDate, List<Training>>,
    modifier: Modifier = Modifier
) {
    var selectedKey by rememberSaveable {
        mutableStateOf(activityData.keys.lastOrNull() ?: LocalDate.now())
    }

    if (activityData.isEmpty()) {
        TextStub(errorMessage = R.string.warning_no_trainings)
        return
    }

    ActivityCalendarRow(
        selectedDate = selectedKey,
        activityStats = activityData.mapValues { it.value.size }.toList(),
        onDaySelected = { selectedKey = it },
    )

    if (activityData[selectedKey]?.isEmpty() == true) {
        TextStub(errorMessage = R.string.stub_no_day_activity)
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(activityData[selectedKey] ?: emptyList()) {
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
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = stringResource(R.string.training_type_walk),
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

@Preview
@Composable
fun HomePreviewEmptyList() {
    HealthTrackerTheme {
        Scaffold {
            HomeBody(
                Resource.Success(emptyMap()),
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
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}