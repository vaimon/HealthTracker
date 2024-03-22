package me.vaimon.healthtracker.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.domain.util.Resource
import me.vaimon.healthtracker.models.TrainingDay
import me.vaimon.healthtracker.navigation.NavigationDestination
import me.vaimon.healthtracker.screens.home.components.ActivityCalendar
import me.vaimon.healthtracker.screens.home.components.LargeActionButton
import me.vaimon.healthtracker.screens.home.components.ResourceLoading
import me.vaimon.healthtracker.screens.home.components.TextStub
import me.vaimon.healthtracker.screens.home.state.ExtendingActivitySheetConnection
import me.vaimon.healthtracker.theme.Dimens
import me.vaimon.healthtracker.theme.HealthTrackerTheme
import me.vaimon.healthtracker.util.ExceptionTranslator
import me.vaimon.healthtracker.util.PreviewSampleData
import me.vaimon.healthtracker.util.conditional
import java.time.LocalDate

object HomeScreenDestination : NavigationDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {
    val trainingList = viewModel.trainings.collectAsState(initial = Resource.Loading)

    HomeBody(
        trainings = trainingList.value, modifier = modifier
    )
}

@Composable
fun HomeBody(
    trainings: Resource<Map<LocalDate, TrainingDay>>, modifier: Modifier = Modifier
) {
    val currentTrainingHeightPx = with(LocalDensity.current) {
        Dimens.currentTrainingFieldHeight.roundToPx()
    }

    val nestedScrollTrainingsConnection = remember {
        ExtendingActivitySheetConnection(currentTrainingHeightPx)
    }

    var isTrainingInProgress by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            HomeAppBar(
                containerColor = if (nestedScrollTrainingsConnection.offset == 0 || isTrainingInProgress) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.background
            )
        },
    ) { screenPadding ->
        Box(
            modifier = modifier
                .nestedScroll(nestedScrollTrainingsConnection)
                .padding(screenPadding)
                .fillMaxWidth(),
        ) {
            CurrentTraining(
                isTrainingInProgress = isTrainingInProgress,
                onTrainingStartClick = { isTrainingInProgress = true },
                onTrainingStopClick = { isTrainingInProgress = false },
                modifier = Modifier.height(Dimens.currentTrainingFieldHeight)
            )
            TrainingActivity(
                trainings = trainings,
                isFullScreen = { nestedScrollTrainingsConnection.offset == 0 },
                modifier = Modifier
                    .padding(top = with(LocalDensity.current) {
                        nestedScrollTrainingsConnection.offset.toDp()
                    })
                    .scrollable(
                        state = rememberScrollState(), orientation = Orientation.Vertical
                    )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    containerColor: Color, modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp
            )
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor
        ), modifier = modifier
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CurrentTraining(
    isTrainingInProgress: Boolean,
    onTrainingStartClick: () -> Unit,
    onTrainingStopClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
                .conditional(isTrainingInProgress,
                    ifTrue = { fillMaxSize() },
                    ifFalse = { clip(RoundedCornerShape(32.dp)) })
                .background(MaterialTheme.colorScheme.primary)
        ) {
            AnimatedVisibility(visible = !isTrainingInProgress) {
                LargeActionButton(
                    icon = R.drawable.icon_walk,
                    text = stringResource(R.string.action_start_walk),
                    onClick = onTrainingStartClick,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            AnimatedVisibility(visible = isTrainingInProgress) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "00:00:00",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(8.dp)
                    ) {
                        ActionIconButton(icon = R.drawable.icon_pause, onClick = { /*TODO*/ })
                        Spacer(modifier = Modifier.size(16.dp))
                        ActionIconButton(
                            icon = R.drawable.icon_stop,
                            onClick = onTrainingStopClick
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ActionIconButton(
    @DrawableRes icon: Int, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ), modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = null
        )
    }
}

@Composable
fun TrainingActivity(
    trainings: Resource<Map<LocalDate, TrainingDay>>,
    isFullScreen: () -> Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .conditional(!isFullScreen()) {
            clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        }
        .background(MaterialTheme.colorScheme.primaryContainer)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.header_last_activity),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(PaddingValues(horizontal = 16.dp))
        )
        when (trainings) {
            is Resource.Error -> TextStub(message = ExceptionTranslator.translate(trainings.exception))
            is Resource.Loading -> ResourceLoading()
            is Resource.Success -> ActivityCalendar(activityData = trainings.data)
        }
    }
}

@Preview
@Composable
fun HomePreviewEmptyList() {
    HealthTrackerTheme {
        Scaffold {
            HomeBody(
                Resource.Success(emptyMap()), modifier = Modifier
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
                Resource.Loading, modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}