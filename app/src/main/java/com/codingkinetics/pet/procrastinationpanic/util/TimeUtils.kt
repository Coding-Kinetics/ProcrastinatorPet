package com.codingkinetics.pet.procrastinationpanic.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@SuppressLint("ConstantLocale")
private val datePickerFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
private val uiFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

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
