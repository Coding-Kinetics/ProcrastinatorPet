package com.codingkinetics.pet.procrastinationpanic.tasks.data.repository

import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TaskLocalSource
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.models.toTask
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.models.toTaskDomain
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.Task
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.Result
import javax.inject.Inject

private const val tag = "TaskRepository"

class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskLocalSource,
    private val logger: Logger,
) : TaskRepository {
    override suspend fun getTask(id: Int): Result<Task> = try {
        when (val task = localDataSource.findById(id)) {
            is Result.Success -> {
                val taskDomain = task.data.toTaskDomain(tag, logger)
                Result.Success(taskDomain)
            }
            is Result.Failure -> Result.Failure(task.error)
        }
    } catch (e: Exception) {
        logger.logError(
            tag,
            "Unable to get task $id. Cause: ${e.message}"
        )
        Result.Failure(e)
    }

    override suspend fun getAllTasks(): Result<List<Task>> = try {
        when (val task = localDataSource.getAll()) {
            is Result.Success -> {
                val taskDomain = task.data.toTaskDomain(tag, logger)
                Result.Success(taskDomain)
            }
            is Result.Failure -> Result.Failure(task.error)
        }
    } catch (e: Exception) {
        logger.logError(
            tag,
            "Unable to retrieve all tasks: ${e.message}"
        )
        Result.Failure(e)
    }

    override suspend fun createTask(task: Task): Result<List<Task>> = try {
        when (val result = localDataSource.insertTask(task.toTask(tag, logger))) {
            is Result.Success -> {
                val taskDomain = result.data.toTaskDomain(tag, logger)
                Result.Success(taskDomain)
            }
            is Result.Failure -> Result.Failure(result.error)
        }
    } catch (e: Exception) {
        logger.logError(
            tag, "Unable to create task ${task.title}: ${e.message}",
        )
        Result.Failure(e)
    }

    override suspend fun updateTask(task: Task): Result<List<Task>> = try {
        when (val result = localDataSource.insertTask(task.toTask(tag, logger))) {
            is Result.Success -> {
                val taskDomain = result.data.toTaskDomain(tag, logger)
                Result.Success(taskDomain)
            }
            is Result.Failure -> Result.Failure(result.error)
        }
    } catch (e: Exception) {
        logger.logError(
            tag,
            "Unable to create task ${task.title}: ${e.message}"
        )
        Result.Failure(e)
    }

    override suspend fun deleteTask(id: Int): Result<List<Task>> {
        TODO()
        /*
        return try {
            val updatedTasks = taskDao.deleteTask(id)
            val tasksDomain = updatedTasks.toTaskDomain(tag, logger)
            Result.Success(tasksDomain)
        } catch(e: Exception) {
            logger.logError(
                tag,
                "Problem deleting task id $id: ${e.message.toString()}"
            )
            Result.Failure(e)
        }*/
    }
}
