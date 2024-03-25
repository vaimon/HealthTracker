package me.vaimon.healthtracker.screens.home.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

class ExtendingActivitySheetConnection(
    private val currentTrainingHeightPx: Int
) : NestedScrollConnection {
    var offset: Int by mutableIntStateOf(currentTrainingHeightPx)
        private set

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val delta = available.y.toInt()
        val newOffset = offset + delta
        val previousOffset = offset
        offset = newOffset.coerceIn(0, currentTrainingHeightPx)
        return Offset(0f, (offset - previousOffset).toFloat())
    }
}