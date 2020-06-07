package com.krossovochkin.kweather.shared.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect val Dispatchers.IO: CoroutineDispatcher
