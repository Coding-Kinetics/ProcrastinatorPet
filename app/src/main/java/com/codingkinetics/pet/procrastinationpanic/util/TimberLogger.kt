package com.codingkinetics.pet.procrastinationpanic.util

import timber.log.Timber
import javax.inject.Inject

interface Logger {
    fun log(message: String)
    fun log(tag: String, message: String)
    fun logError(tag: String, errorMessage: String)
}

class TimberLogger @Inject constructor() : Logger {
    override fun log(message: String) {
        Timber.d(message)
    }

    override fun log(tag: String, message: String) {
        Timber.d("$tag: $message")
    }

    override fun logError(tag: String, errorMessage: String) {
        Timber.e("CAUSE OF ERROR - $tag: $errorMessage")
    }
}
