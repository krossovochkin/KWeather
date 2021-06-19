package com.krossovochkin.kweather.setup.data.json

internal data class JsonToken(
    val type: Type,
    val value: Any?
) {

    enum class Type {
        BEGIN_ARRAY,
        END_ARRAY,
        BEGIN_OBJECT,
        END_OBJECT,
        COLON,
        COMMA,
        BOOLEAN,
        STRING,
        NUMBER,
        NULL,
        WHITESPACE
    }
}
