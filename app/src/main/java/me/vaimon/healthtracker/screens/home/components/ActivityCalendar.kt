package me.vaimon.healthtracker.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import me.vaimon.healthtracker.R
import me.vaimon.healthtracker.models.TrainingDay
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActivityCalendar(
    activityData: Map<LocalDate, TrainingDay>,
    modifier: Modifier = Modifier
) {
    val keys = remember {
        activityData.keys.toList()
    }
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = keys.size - 1,
        pageCount = {
            keys.size
        }
    )

    if (activityData.isEmpty()) {
        TextStub(message = R.string.warning_no_trainings, modifier = modifier)
        return
    }

    ActivityCalendarRow(
        selectedDate = activityData.keys.elementAt(pagerState.currentPage),
        activityStats = activityData.mapValues { it.value.totalTrainingTime.toInt() }.toList(),
        onDaySelected = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(keys.indexOf(it))
            }
        },
        modifier = modifier
    )

    HorizontalPager(
        state = pagerState,
    ) { page ->
        if (activityData[keys[page]]?.trainings?.isEmpty() == true) {
            TextStub(message = R.string.stub_no_day_activity)
        } else {
            TrainingDayStats(trainingDay = activityData.getValue(keys[page]))
        }
    }
}