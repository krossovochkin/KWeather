package com.krossovochkin.kweather.shared.feature.setup.data.json

data class JsonParseException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause)