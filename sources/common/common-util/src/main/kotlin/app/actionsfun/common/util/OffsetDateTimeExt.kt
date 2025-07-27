package app.actionsfun.common.util

import java.time.Duration
import java.time.OffsetDateTime
import kotlin.math.abs

fun OffsetDateTime.timeLeftString(): String {
    val now = OffsetDateTime.now()
    val duration = Duration.between(now, this)

    return when {
        duration.isNegative -> {
            val absDuration = duration.abs()
            val hours = absDuration.toHours()
            val minutes = absDuration.toMinutesPart()
            val seconds = absDuration.toSecondsPart()
            "-${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        }
        else -> {
            val hours = duration.toHours()
            val minutes = duration.toMinutesPart()
            val seconds = duration.toSecondsPart()
            "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        }
    }
}

fun OffsetDateTime.timeRelativeString(): String {
    val now = OffsetDateTime.now()
    val duration = Duration.between(this, now)
    val seconds = duration.seconds
    val absSeconds = abs(seconds)

    return when {
        absSeconds < 60 -> "just now"
        absSeconds < 3600 -> {
            val minutes = absSeconds / 60
            if (seconds > 0) "${minutes}m ago" else "in ${minutes}m"
        }
        absSeconds < 86400 -> {
            val hours = absSeconds / 3600
            if (seconds > 0) "${hours}hr ago" else "in ${hours}hr"
        }
        absSeconds < 2592000 -> {
            val days = absSeconds / 86400
            if (seconds > 0) "${days}d ago" else "in ${days}d"
        }
        absSeconds < 31536000 -> {
            val months = absSeconds / 2592000
            if (seconds > 0) "${months}mo ago" else "in ${months}mo"
        }
        else -> {
            val years = absSeconds / 31536000
            if (seconds > 0) "${years}yr ago" else "in ${years}yr"
        }
    }
}