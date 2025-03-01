package com.codingkinetics.pet.procrastinationpanic.util

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val mainDispatcher: CoroutineContext
    val defaultDispatcher: CoroutineContext
    val ioDispatcher: CoroutineContext
}

class CoroutineContextProviderImpl @Inject constructor() : CoroutineContextProvider {
    override val mainDispatcher: CoroutineContext by lazy { Dispatchers.Main }
    override val defaultDispatcher: CoroutineContext by lazy { Dispatchers.Default }
    override val ioDispatcher: CoroutineContext by lazy { Dispatchers.IO }
}
