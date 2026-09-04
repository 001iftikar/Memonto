package com.iftikar.memonto.core.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun formatRelativeTime(updatedAtMillis: Long, currentTimeMillis: Long): String {
    // 1. Convert raw milliseconds into Instants
    val updatedInstant = Instant.fromEpochMilliseconds(updatedAtMillis)
    val nowInstant = Instant.fromEpochMilliseconds(currentTimeMillis)

    // 2. Determine the device's local timezone
    val tz = TimeZone.currentSystemDefault()

    // 3. Extract purely the calendar date (ignores hours/minutes)
    val updatedDate = updatedInstant.toLocalDateTime(tz).date
    val nowDate = nowInstant.toLocalDateTime(tz).date

    // 4. Calculate calendar days between the two dates
    val daysDifference = updatedDate.daysUntil(nowDate)

    return when {
        daysDifference <= 0 -> {
            // It happened today. Calculate the exact duration.
            val duration = nowInstant - updatedInstant
            val hours = duration.inWholeHours
            val minutes = duration.inWholeMinutes

            when {
                hours > 0 -> "Updated $hours ${if (hours == 1L) "hour" else "hours"} ago"
                minutes > 0 -> "Updated $minutes ${if (minutes == 1L) "min" else "mins"} ago"
                else -> "Updated just now"
            }
        }
        daysDifference == 1 -> "Yesterday"
        else -> "$daysDifference days ago"
    }
}

/**
 * To greet the user
 */
fun getTimeOfDay(epochMillis: Long): TimeOfDay {
    // 1. Convert the raw scalar milliseconds into a global Instant
    val instant = Instant.fromEpochMilliseconds(epochMillis)

    // 2. Apply the user's specific physical timezone
    val localTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    // 3. Extract the 24-hour integer and map it to a greeting
    return when (localTime.hour) {
        in 5..11 -> TimeOfDay.MORNING
        in 12..16 -> TimeOfDay.AFTERNOON
        in 17..20 -> TimeOfDay.EVENING
        else -> TimeOfDay.NIGHT
    }
}

/**
 * To greet the user
 */
enum class TimeOfDay {
    MORNING, AFTERNOON, EVENING, NIGHT
}