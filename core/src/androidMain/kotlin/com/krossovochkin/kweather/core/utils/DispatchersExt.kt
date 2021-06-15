package com.krossovochkin.kweather.core.utils

import kotlinx.coroutines.Dispatchers

actual val Dispatchers.IO
    get() = IO
