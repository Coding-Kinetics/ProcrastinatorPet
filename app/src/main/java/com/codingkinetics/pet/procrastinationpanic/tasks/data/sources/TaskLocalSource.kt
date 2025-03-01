package com.codingkinetics.pet.procrastinationpanic.tasks.data.sources

import com.codingkinetics.pet.procrastinationpanic.TaskEntity
import com.codingkinetics.pet.procrastinationpanic.util.Result

interface TaskLocalSource {
    suspend fun findById(id: Int): Result<TaskEntity>

    suspend fun insertTask(task: TaskEntity): Result<List<TaskEntity>>

    suspend fun getAll(): Result<List<TaskEntity>>
}
