package com.codingkinetics.pet.procrastinationpanic.tasks.domain

import com.codingkinetics.pet.procrastinationpanic.util.Logger
import java.time.LocalDate
import kotlin.random.Random

data class Task(
    val id: Long = Random.nextLong(),
    val title: String = "",
    val description: String = "",
    val state: TaskState = TaskState.NOT_STARTED,
    val priority: TaskPriority = TaskPriority.entries[1],
    val dateCreated: LocalDate? = LocalDate.now(),
    val dueDate: LocalDate? = null,
)

enum class TaskPriority {
    Important,
    Normal,
}

fun String.toTaskPriority(
    tag: String,
    logger: Logger,
): TaskPriority {
    return try {
        TaskPriority.valueOf(this)
    } catch (e: IllegalStateException) {
        logger.logError(tag, "Unable to get value of $this. ${e.message}")
        TaskPriority.Normal
    }
}

enum class TaskState {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    OVERDUE,
}

fun String.toTaskState(
    tag: String,
    logger: Logger,
): TaskState {
    return try {
        TaskState.valueOf(this)
    } catch (e: IllegalStateException) {
        logger.logError(tag, "Unable to get value of $this. ${e.message}")
        TaskState.NOT_STARTED
    }
}
