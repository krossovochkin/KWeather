package com.krossovochkin.test

import kotlinx.coroutines.CoroutineScope

expect fun runBlockingTest(block: suspend CoroutineScope.() -> Unit)
