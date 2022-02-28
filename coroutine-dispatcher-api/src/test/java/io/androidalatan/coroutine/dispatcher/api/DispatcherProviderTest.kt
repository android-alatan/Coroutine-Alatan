package io.androidalatan.coroutine.dispatcher.api

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DispatcherProviderTest {

    private val dispatcherProvider = DispatcherProvider()

    @Test
    fun default() {
        Assertions.assertEquals("Dispatchers.Default", dispatcherProvider.default()
            .toString())
    }

    @Test
    fun io() {
        Assertions.assertEquals("Dispatchers.IO", dispatcherProvider.io()
            .toString())
    }

    @Test
    fun main() {
        Assertions.assertTrue(dispatcherProvider.main()
                                  .toString()
                                  .contains("Dispatchers.Main"))
    }

    @Test
    fun test() {
        Assertions.assertEquals("Dispatchers.Unconfined", dispatcherProvider.test()
            .toString())
    }

}