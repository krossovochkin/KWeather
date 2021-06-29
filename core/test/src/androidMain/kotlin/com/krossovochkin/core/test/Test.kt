package com.krossovochkin.core.test

import kotlinx.coroutines.CoroutineScope

actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) =
    kotlinx.coroutines.test.runBlockingTest { block() }
