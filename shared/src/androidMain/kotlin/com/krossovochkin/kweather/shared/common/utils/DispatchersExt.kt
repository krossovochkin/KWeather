package com.krossovochkin.kweather.shared.common.utils

import kotlinx.coroutines.Dispatchers

actual val Dispatchers.IO
    get() = IO
