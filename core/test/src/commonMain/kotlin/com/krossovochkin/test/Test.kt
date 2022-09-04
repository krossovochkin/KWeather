package com.krossovochkin.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.TestScope

fun <T> Flow<T>.test(scope: TestScope): TestObserver<T> {
    return TestObserver(scope, this)
}
