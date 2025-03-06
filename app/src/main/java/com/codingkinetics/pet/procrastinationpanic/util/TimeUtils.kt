package com.codingkinetics.pet.procrastinationpanic.util

import android.annotation.SuppressLint
import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@SuppressLint("ConstantLocale")
private val datePickerFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
private val uiFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
private val calendar = Calendar.getInstance()

fun Long.convertMillisToDate(
    tag: String,
    logger: Logger,
): String = try {
    datePickerFormatter.format(Date(this))
} catch (e: Exception) {
    logger.logError(tag, "$e")
    ""
}

fun String.convertDateToMillis(
    tag: String,
    logger: Logger,
    task: String? = null,
): Long = try {
    datePickerFormatter.parse(this)?.time ?: 0
} catch (e: Exception) {
    logger.logError(tag, "Couldn't parse String $this into Long for $task: $e")
    0L
}

fun String.convertToLocalDate(tag: String, logger: Logger): LocalDate = try {
    LocalDate.parse(this)
} catch (e: Exception) {
    logger.logError(tag, "$e")
    LocalDate.now()
}

fun LocalDate.toStringDate(
    tag: String,
    logger: Logger,
    task: String? = null,
): String {
    val stringISO = this.toString()
    return try {
        return stringISO.format(datePickerFormatter)
    } catch (e: Exception) {
        logger.logError(tag, "Couldn't parse $stringISO to String for $task: $e")
        ""
    }
}

fun Long.convertToLocalDate(
    tag: String,
    logger: Logger,
): LocalDate {
    return try {
        val millis = this.convertMillisToDate(tag, logger)
        return millis.convertToLocalDate(tag, logger)
    } catch (e: Exception) {
        logger.logError(tag, "$e")
        LocalDate.now()
    }
}

fun Long.getNext8am(): Calendar {
    val next8am = calendar
    next8am.timeInMillis = this
    next8am.set(Calendar.HOUR_OF_DAY, 8)
    next8am.set(Calendar.MINUTE, 0)
    next8am.set(Calendar.SECOND, 0)

    // if it's already past 8am, schedule to next day
    if (next8am.timeInMillis <= this) {
        next8am.add(Calendar.DAY_OF_YEAR, 1)
    }

    return next8am
}
