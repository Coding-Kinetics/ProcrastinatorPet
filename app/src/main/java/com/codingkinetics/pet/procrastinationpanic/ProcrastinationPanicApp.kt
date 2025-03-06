package com.codingkinetics.pet.procrastinationpanic

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.codingkinetics.pet.procrastinationpanic.pet.OverdueTaskWorker
import com.codingkinetics.pet.procrastinationpanic.util.getNext8am
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val OVERDUE_TASK_WORKER = "OverdueTaskWorker"

@HiltAndroidApp
class ProcrastinationPanicApp : Application() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        isWorkScheduled(OVERDUE_TASK_WORKER) {
            setupPetStatusWorkManager()
        }
    }

    private fun isWorkScheduled(workerName: String, startWorker: () -> Unit) {
        workManager.getWorkInfosForUniqueWork(workerName)
            .get()
            .let { workInfo ->
                val isScheduled = workInfo.any {
                    it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING
                }
                if (!isScheduled) {
                    startWorker()
                }
            }
    }

    /***
     * To schedule for 8am every day, calculate the initial delay between now and 8am,
     * then repeat every 24 hours
     */
    private fun setupPetStatusWorkManager() {
        val intialDelay = getInitialTimeDelay()

        val workRequest = PeriodicWorkRequestBuilder<OverdueTaskWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(intialDelay, TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            OVERDUE_TASK_WORKER,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun getInitialTimeDelay(): Long {
        val currentTime = System.currentTimeMillis()
        val next8am = currentTime.getNext8am()

        return next8am.timeInMillis - currentTime
    }
}
