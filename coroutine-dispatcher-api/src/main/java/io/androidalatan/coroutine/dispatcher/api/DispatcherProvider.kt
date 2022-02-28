package io.androidalatan.coroutine.dispatcher.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    fun default(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun test(): CoroutineDispatcher

    companion object {
        operator fun invoke(): DispatcherProvider = object : DispatcherProvider {

            override fun default(): CoroutineDispatcher = Dispatchers.Default

            override fun io(): CoroutineDispatcher = Dispatchers.IO

            override fun main(): CoroutineDispatcher = Dispatchers.Main

            override fun test(): CoroutineDispatcher = Dispatchers.Unconfined
        }
    }
}