package com.codingkinetics.pet.procrastinationpanic.tasks.data.repository

import com.codingkinetics.pet.procrastinationpanic.tasks.domain.Task
import com.codingkinetics.pet.procrastinationpanic.util.Result

interface TaskRepository {
    suspend fun getTask(id: Int): Result<Task>

    suspend fun getAllTasks(): Result<List<Task>>

    /**
     * Add task, return updated task list
     */
    suspend fun createTask(task: Task): Result<List<Task>>

    /**
     * Update task, return updated task list
     */
    suspend fun updateTask(task: Task): Result<List<Task>>

    /**
     * Delete task, return updated task list
     */
    suspend fun deleteTask(id: Int): Result<List<Task>>
}
