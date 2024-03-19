package me.vaimon.healthtracker.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import me.vaimon.healthtracker.screens.home.components.LargeActionButton
import me.vaimon.healthtracker.screens.home.components.ResourceLoading
import me.vaimon.healthtracker.screens.home.components.TextStub
import me.vaimon.healthtracker.theme.HealthTrackerTheme
import me.vaimon.healthtracker.util.ExceptionTranslator
import me.vaimon.healthtracker.util.PreviewSampleData
import me.vaimon.healthtracker.util.conditional
import me.vaimon.healthtracker.util.vertical
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

@Composable
fun ActivityCalendar(
    activityData: Map<LocalDate, List<Training>>,
    modifier: Modifier = Modifier
) {
    var selectedKey by rememberSaveable { mutableStateOf(LocalDate.now()) }

    if (activityData.isEmpty()) {
        TextStub(errorMessage = R.string.warning_no_trainings)
        return
    }

    ActivityCalendarRow(
        selectedDate = selectedKey,
        activityStats = activityData.mapValues { it.value.size }.toList().reversed(),
        onDaySelected = { selectedKey = it },
    )

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(activityData[selectedKey] ?: emptyList()) {
            Text(it.id.toString() + "/ " + it.startTime.toString())
        }
    }
}

@Composable
private fun ActivityCalendarRow(
    selectedDate: LocalDate,
    activityStats: List<Pair<LocalDate, Int>>,
    onDaySelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val datePattern = remember {
        DateTimeFormatter.ofPattern("dd.MM")
    }

    val graphScaling = remember {
        1.0 / (activityStats.maxBy { it.second }.second)
    }

    LazyRow(
        reverseLayout = true,
        horizontalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(activityStats.reversed()) { dateToActivity ->
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .conditional(selectedDate == dateToActivity.first) {
                        background(MaterialTheme.colorScheme.surface)
                    }
                    .clickable {
                        onDaySelected(dateToActivity.first)
                    }
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val canvasSize = 100
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.defaultMinSize(minHeight = canvasSize.dp)
                ) {
                    Spacer(
                        Modifier
                            .width(16.dp)
                            .height((canvasSize * graphScaling * (dateToActivity.second)).dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    dateToActivity.first.format(datePattern),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .vertical()
                        .rotate(-90f)
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