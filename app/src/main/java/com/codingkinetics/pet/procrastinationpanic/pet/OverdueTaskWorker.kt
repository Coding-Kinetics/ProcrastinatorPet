package com.codingkinetics.pet.procrastinationpanic.pet

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.codingkinetics.pet.procrastinationpanic.R
import com.codingkinetics.pet.procrastinationpanic.pet.domain.PetState
import com.codingkinetics.pet.procrastinationpanic.pet.domain.usecase.CalculatePetStatusUseCase
import com.codingkinetics.pet.procrastinationpanic.tasks.data.repository.TaskRepository
import com.codingkinetics.pet.procrastinationpanic.util.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val PROCRASTINATION_PET_ID = "ProcrastinationPetId"

class OverdueTaskWorker @Inject constructor(
    private val appContext: Context,
    workerParameters: WorkerParameters,
    private val repository: TaskRepository,
    private val calculatePetStatusUseCase: CalculatePetStatusUseCase,
    private val contextPool: CoroutineContextProvider,
) : Worker(appContext, workerParameters) {

    private val scope = CoroutineScope(contextPool.ioDispatcher)

    override fun doWork(): Result {
        // perform background task
        scope.launch {
            when (val petState = calculatePetStatus()) {
                PetState.Happy, PetState.Elated, PetState.Loved -> { /* do nothing */ }
                PetState.Panicking -> withContext(contextPool.mainDispatcher) {
                    /* send notification */
                }
                PetState.Sad -> withContext(contextPool.mainDispatcher) {
                    /* send notification */
                }
            }
        }
        return Result.success()
    }

    private suspend fun calculatePetStatus(): PetState {
        return when (val tasks = repository.getAllTasks()) {
            is com.codingkinetics.pet.procrastinationpanic.util.Result.Success -> {
                calculatePetStatusUseCase.invoke(tasks.data)
            }
            is com.codingkinetics.pet.procrastinationpanic.util.Result.Failure -> {
                // TODO log failure
                // TODO return Result.Failure but for now default is fine
                PetState.Happy
            }
        }
    }

    private fun buildNotification(
        name: String = "Amanda",
        petName: String = "Blake",
    ) {
        val builder = NotificationCompat.Builder(appContext, PROCRASTINATION_PET_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Hey $name, Blake is starting to panic")
            .setContentText("You have some overdue tasks to address!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}
