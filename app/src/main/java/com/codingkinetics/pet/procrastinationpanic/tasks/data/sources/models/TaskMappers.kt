package com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.models

import com.codingkinetics.pet.procrastinationpanic.TaskEntity
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.Task
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.toTaskPriority
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.toTaskState
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.convertToLocalDate
import com.codingkinetics.pet.procrastinationpanic.util.toStringDate
import java.time.LocalDate

fun List<TaskEntity>.toTaskDomain(
    tag: String,
    logger: Logger,
): List<Task> {
    return this.map { task ->
        task.toTaskDomain(tag, logger)
    }
}

fun TaskEntity.toTaskDomain(
    tag: String,
    logger: Logger,
): Task {
    return Task(
        id = this.uid,
        title = this.title,
        description = this.description ?: "",
        state = this.state.toTaskState(tag, logger),
        priority = this.priority.toTaskPriority(tag, logger),
        dueDate = this.date_due.convertToLocalDate(tag, logger),
    )
}

fun List<Task>.toTasks(
    tag: String,
    logger: Logger,
): List<TaskEntity> {
    return this.map { taskDomain ->
        taskDomain.toTask(tag, logger)
    }
}

fun Task.toTask(
    tag: String,
    logger: Logger,
): TaskEntity {
    return TaskEntity(
        uid = id,
        title = title,
        description = description,
        state = state.toString(),
        priority = priority.toString(),
        date_created =
            dateCreated?.toStringDate(
                tag,
                logger,
                title,
            ) ?: LocalDate.now().toStringDate(tag, logger),
        date_due = dueDate?.toStringDate(tag, logger, title) ?: "",
    )
}
