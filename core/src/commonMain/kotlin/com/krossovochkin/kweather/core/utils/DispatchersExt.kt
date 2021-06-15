package com.krossovochkin.kweather.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect val Dispatchers.IO: CoroutineDispatcher
