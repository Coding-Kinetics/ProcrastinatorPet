package com.codingkinetics.pet.procrastinationpanic.pet

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Process
import android.widget.Toast

/**
 * Fun fact, Android's UI thread is a looper created from the application's process main thread
 * Looper/Handler is a framework of cooperating classes: Looper, MessageQueue, and Messages +
 * Handler(s)
 *
 * A Looper is a Java Thread initialized by calling Looper.prepare() and Looper.start()
 * in the run method.
 *
 * ```
 * val looper = Thread {
 *     Looper.prepare()
 *     Looper.loop() // causes the thread to enter a tight loop to check MessageQueue for tasks
 * }.start()
 * ```
 *
 * A Handler is the mechanism used to enqueue tasks on a Looper's queue for processing i.e.
 * - post(task: Runnable) - attach a Runnable to message for special handling
 * - send(task: Message) - enqueues message to looper
 *
 * In the code example above, we use post to enqueue a task. Handler obtains a message object form
 * a pool, attaches it to Runnable, and adds the Message to the end of the Looper's MessageQueue.
 *
 */
class PetStatusCalculation : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            try {
                Thread.sleep(5000) // do work here i.e. download file
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt() // restore interrupt status
            }
            // stop the service using startId so we don't stop the service in the middle
            // of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(
        intent: Intent,
        flags: Int,
        startId: Int,
    ): Int {
        Toast.makeText(this, "serviceStarting", Toast.LENGTH_SHORT).show()

        serviceHandler?.obtainMessage()?.let { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        return START_STICKY // if we get killed after returning, restart
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
        serviceLooper = null
        serviceHandler = null
    }
}
