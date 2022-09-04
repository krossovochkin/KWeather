package com.krossovochkin.test

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestObserver<T>(
    scope: TestScope,
    flow: Flow<T>
) {

    private val values = mutableListOf<T>()
    private val job: Job = scope.launch {
        flow.collect { values.add(it) }
    }

    fun assertValueCount(size: Int): TestObserver<T> = apply {
        assertEquals(size, this.values.size)
    }

    fun assertNoValues(): TestObserver<T> = apply {
        assertEquals(emptyList(), this.values)
    }

    fun assertValues(vararg values: T): TestObserver<T> = apply {
        assertEquals(values.toList(), this.values)
    }

    fun assertLatestValue(value: T): TestObserver<T> = apply {
        assertEquals(value, this.values.last())
    }

    fun assertLatestValue(block: (T) -> Boolean) = apply {
        assertTrue(block(this.values.last()))
    }

    fun finish() {
        job.cancel()
    }
}
