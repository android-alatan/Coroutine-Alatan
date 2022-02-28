package io.androidalatan.coroutine.dispatcher.api.assertion

import io.androidalatan.coroutine.test.turbine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MockDispatcherProviderTest {

    private val schedulerProvider = MockDispatcherProvider()

    @Test
    fun immediate() {
        schedulerProvider.immediate()
        Assertions.assertTrue(schedulerProvider.dispatchImmediately)
    }

    @Test
    fun async() {
        schedulerProvider.async()
        Assertions.assertFalse(schedulerProvider.dispatchImmediately)
    }

    @Test
    fun `default async`() {
        schedulerProvider.async()
        Assertions.assertEquals(schedulerProvider.testScheduler, schedulerProvider.default())
    }

    @Test
    fun `io async`() {
        schedulerProvider.async()
        Assertions.assertEquals(schedulerProvider.testScheduler, schedulerProvider.io())
    }

    @Test
    fun `main async`() {
        schedulerProvider.async()
        Assertions.assertEquals(schedulerProvider.testScheduler, schedulerProvider.main())
    }

    @Test
    fun `test async`() {
        schedulerProvider.async()
        Assertions.assertEquals(schedulerProvider.testScheduler, schedulerProvider.test())
    }

    @Test
    fun `default immediate`() {
        schedulerProvider.immediate()
        Assertions.assertEquals(Dispatchers.Unconfined::class.java, schedulerProvider.default().javaClass)
    }

    @Test
    fun `io immediate`() {
        schedulerProvider.immediate()
        Assertions.assertEquals(Dispatchers.Unconfined::class.java, schedulerProvider.io().javaClass)
    }

    @Test
    fun `main immediate`() {
        schedulerProvider.immediate()
        Assertions.assertEquals(Dispatchers.Unconfined::class.java, schedulerProvider.main().javaClass)
    }

    @Test
    fun `test immediate`() {
        schedulerProvider.immediate()
        Assertions.assertEquals(Dispatchers.Unconfined::class.java, schedulerProvider.test().javaClass)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun advanceTimeBy() {
        schedulerProvider.advanceTimeBy(delayTimeMillis = 1_000)
        Assertions.assertEquals(1_000, schedulerProvider.testScheduler.currentTime)

        schedulerProvider.advanceTimeBy(delayTimeMillis = 5_000)
        Assertions.assertEquals(6_000, schedulerProvider.testScheduler.currentTime)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `flow advanceTimeBy`() {
        schedulerProvider.async()
        flow {
            emit(3)
        }
            .onStart {
                delay(5_000L)
            }
            .flowOn(schedulerProvider.test())
            .turbine { flowTurbine ->
                val retTime = schedulerProvider.advanceTimeBy(5_001L)
                Assertions.assertEquals(5_001L, retTime)
                Assertions.assertEquals(3, flowTurbine.awaitItem())
                flowTurbine.cancelAndConsumeRemainingEvents()
            }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun advanceUntilIdle() = runBlocking {
        val delayTime = 5_000L
        schedulerProvider.async()
        flow {
            emit(3)
        }
            .onStart {
                delay(delayTime)
            }
            .flowOn(schedulerProvider.test())
            .turbine { flowTurbine ->
                val retTime = schedulerProvider.advanceUntilIdle()
                Assertions.assertEquals(delayTime, retTime)
                Assertions.assertEquals(3, flowTurbine.awaitItem())
                flowTurbine.cancelAndConsumeRemainingEvents()
            }
    }
}