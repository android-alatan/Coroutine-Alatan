package io.androidalatan.coroutine.dispatcher.api.assertion

import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

class MockDispatcherProvider : DispatcherProvider {

    @OptIn(ExperimentalCoroutinesApi::class)

    internal val testScheduler = TestCoroutineDispatcher()

    internal var dispatchImmediately = true

    fun immediate(): MockDispatcherProvider {
        dispatchImmediately = true
        return this
    }

    fun async(): MockDispatcherProvider {
        dispatchImmediately = false
        return this
    }

    override fun default(): CoroutineDispatcher {
        return if (dispatchImmediately) {
            Dispatchers.Unconfined
        } else {
            testScheduler
        }
    }

    override fun io(): CoroutineDispatcher {
        return if (dispatchImmediately) {
            Dispatchers.Unconfined
        } else {
            testScheduler
        }
    }

    override fun main(): CoroutineDispatcher {
        return if (dispatchImmediately) {
            Dispatchers.Unconfined
        } else {
            testScheduler
        }
    }

    override fun test(): CoroutineDispatcher {
        return if (dispatchImmediately) {
            Dispatchers.Unconfined
        } else {
            testScheduler
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun advanceTimeBy(delayTimeMillis: Long): Long =
        testScheduler.advanceTimeBy(delayTimeMillis)

    @ExperimentalCoroutinesApi
    fun advanceUntilIdle(): Long =
        testScheduler.advanceUntilIdle()

    @ExperimentalCoroutinesApi
    fun triggerActions(): MockDispatcherProvider {
        testScheduler.runCurrent()
        return this
    }

}