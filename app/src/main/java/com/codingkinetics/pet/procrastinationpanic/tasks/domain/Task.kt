package com.codingkinetics.pet.procrastinationpanic.tasks.domain

import com.codingkinetics.pet.procrastinationpanic.util.Logger
import java.time.LocalDate
import kotlin.random.Random

data class Task(
    val id: Long = Random.nextLong(),
    val title: String = "",
    val description: String = "",
    val state: TaskState = TaskState.NotStarted,
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
): TaskPriority = try {
    TaskPriority.valueOf(this)
} catch (e: IllegalStateException) {
    logger.logError(tag, "Unable to get value of $this. ${e.message}")
    TaskPriority.Normal
}

enum class TaskState {
    NotStarted,
    InProgress,
    Completed,
    Overdue,
}

fun String.toTaskState(
    tag: String,
    logger: Logger,
): TaskState = try {
    TaskState.valueOf(this)
} catch (e: IllegalStateException) {
    logger.logError(tag, "Unable to get value of $this. ${e.message}")
    TaskState.NotStarted
}
