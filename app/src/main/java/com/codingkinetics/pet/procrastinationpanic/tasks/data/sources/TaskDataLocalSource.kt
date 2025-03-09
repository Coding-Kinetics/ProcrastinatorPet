package com.codingkinetics.pet.procrastinationpanic.tasks.data.sources

import com.codingkinetics.pet.procrastinationpanic.Database
import com.codingkinetics.pet.procrastinationpanic.TaskEntity
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.Result
import javax.inject.Inject
import javax.inject.Named

const val TAG = "TaskLocalDataSource"

class TaskDataLocalSource @Inject constructor(
    @Named("TaskDatabase") private val database: Database,
    private val logger: Logger,
) : TaskLocalSource {
        override suspend fun findById(id: Int): Result<TaskEntity> =
            try {
                val list = database.taskQueries.findById(id.toLong()).executeAsOne()
                Result.Success(list)
            } catch (e: Exception) {
                logger.logError(TAG, "Unable to insert task. Cause: $e")
                Result.Failure(e)
            }

        override suspend fun insertTask(task: TaskEntity): Result<List<TaskEntity>> =
            try {
                val list =
                    database.transactionWithResult {
                        database.taskQueries.insertTask(task)
                        database.taskQueries.selectAll().executeAsList()
                    }
                Result.Success(list)
            } catch (e: Exception) {
                logger.logError(TAG, "Unable to insert task. Cause: $e")
                Result.Failure(e)
            }

        override suspend fun getAll(): Result<List<TaskEntity>> =
            try {
                val list = database.taskQueries.selectAll().executeAsList()
                Result.Success(list)
            } catch (e: Exception) {
                logger.logError(TAG, "Unable to insert task. Cause: $e")
                Result.Failure(e)
            }

        override suspend fun deleteTask(id: Int): Result<Boolean> =
            try {
                database.taskQueries.deleteTask(id.toLong())
                Result.Success(true)
            } catch (e: Exception) {
                logger.logError(TAG, "Unable to delete task. Cause: $e")
                Result.Failure(e)
            }
    }
