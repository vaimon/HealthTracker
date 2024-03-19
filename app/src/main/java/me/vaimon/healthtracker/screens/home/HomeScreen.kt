package me.vaimon.healthtracker.screens.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.domain.util.Resource
import me.vaimon.healthtracker.models.Training
import me.vaimon.healthtracker.navigation.NavigationDestination
import me.vaimon.healthtracker.theme.HealthTrackerTheme
import me.vaimon.healthtracker.theme.labelError
import me.vaimon.healthtracker.util.ExceptionTranslator

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
    trainings: Resource<List<Training>>,
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
            is Resource.Error -> ErrorStub(errorMessage = ExceptionTranslator.translate(trainings.exception))
            is Resource.Loading -> ResourceLoading()
            is Resource.Success -> ActivityCalendar(activityData = trainings.data)
        }
    }
}

@Composable
fun ActivityCalendar(
    activityData: List<Training>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(activityData) {
            Text(it.id.toString() + "/ " + it.startTime.toString())
        }
    }
}

@Composable
fun ResourceLoading(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorStub(
    @StringRes errorMessage: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = stringResource(id = errorMessage),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelError,
        )
    }
}

@Composable
fun LargeActionButton(
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 64.dp),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun HomePreview() {
    HealthTrackerTheme {
        Scaffold {
            HomeBody(
                Resource.Success(emptyList()),
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