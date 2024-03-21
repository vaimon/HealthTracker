package me.vaimon.healthtracker.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.vaimon.healthtracker.util.Formatter
import me.vaimon.healthtracker.util.conditional
import me.vaimon.healthtracker.util.vertical
import java.time.LocalDate

@Composable
fun ActivityCalendarRow(
    selectedDate: LocalDate,
    activityStats: List<Pair<LocalDate, Int>>,
    onDaySelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val graphScaling = remember {
        1.0f / (activityStats.maxBy { it.second }.second)
    }

    LazyRow(
        reverseLayout = true,
        horizontalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(activityStats.reversed()) { dateToActivity ->
            AggregationDateColumn(
                date = dateToActivity.first,
                isSelected = selectedDate == dateToActivity.first,
                onDaySelected = onDaySelected,
                graphScaling = graphScaling,
                activityMeasure = dateToActivity.second,
            )
        }
    }
}

@Composable
private fun AggregationDateColumn(
    date: LocalDate,
    isSelected: Boolean,
    onDaySelected: (LocalDate) -> Unit,
    graphScaling: Float,
    activityMeasure: Int,
    modifier: Modifier = Modifier,
    columnHeight: Dp = 100.dp
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .conditional(isSelected) {
                background(MaterialTheme.colorScheme.surface)
            }
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                onDaySelected(date)
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.defaultMinSize(minHeight = columnHeight)
        ) {
            Spacer(
                Modifier
                    .width(16.dp)
                    .height(columnHeight * graphScaling * activityMeasure)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            date.format(Formatter.labelDateFormatter),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .vertical()
                .rotate(-90f)
        )
    }
}