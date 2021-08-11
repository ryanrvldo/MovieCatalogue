package com.ryanrvldo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class TestCoroutineExtension : BeforeEachCallback, AfterEachCallback {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    val scope = TestCoroutineScope(testCoroutineDispatcher)

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
        try {
            scope.cleanupTestCoroutines()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        scope.runBlockingTest { block() }
    }

}