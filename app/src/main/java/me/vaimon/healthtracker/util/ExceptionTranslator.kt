package me.vaimon.healthtracker.util

import me.vaimon.healthtracker.R

object ExceptionTranslator {
    fun translate(exception: Throwable): Int {
        return when (exception) {
            is IllegalStateException -> R.string.error_illegal_state
            else -> R.string.error_unknown
        }
    }
}