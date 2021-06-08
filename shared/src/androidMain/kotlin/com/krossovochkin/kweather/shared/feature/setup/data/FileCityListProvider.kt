package com.krossovochkin.kweather.shared.feature.setup.data

import android.content.Context
import android.util.Log
import com.krossovochkin.kweather.shared.common.storage.CityDto
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonArray
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonCharIterator
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonElement
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonObject
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonParseException
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonProperty
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonToken
import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonTokenizer
import com.krossovochkin.kweather.shared.feature.setup.data.json.iterator
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

private const val FILE_NAME_CITY_LIST = "city.list.json"

actual class FileCityListProvider(
    private val context: Context
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    actual fun observe(): Flow<CityDto> {
        return flow {
            val input = context.assets.open(FILE_NAME_CITY_LIST)
            val tokenizer = JsonTokenizer(JsonCharIterator(input.iterator()))

            tokenizer.next().checkType(JsonToken.Type.BEGIN_ARRAY)

            var hasItems = false

            var nextToken = tokenizer.next()
            while (nextToken.type != JsonToken.Type.END_ARRAY) {
                if (!currentCoroutineContext().isActive) {
                    return@flow
                }

                if (hasItems) {
                    nextToken.checkType(JsonToken.Type.COMMA)
                    nextToken = tokenizer.next()
                }

                hasItems = true

                emit(tokenizer.parseJsonElement(startToken = nextToken).toDto())

                nextToken = tokenizer.next()
            }
        }
    }

    private fun Iterator<JsonToken>.parseJsonArray(): JsonArray {
        val array = JsonArray()

        var nextToken = this.next()
        while (nextToken.type != JsonToken.Type.END_ARRAY) {
            if (array.children.isNotEmpty()) {
                nextToken.checkType(JsonToken.Type.COMMA)
                nextToken = this.next()
            }

            array.add(this.parseJsonElement(startToken = nextToken))

            nextToken = this.next()
        }

        return array
    }

    private fun Iterator<JsonToken>.parseJsonObject(): JsonObject {
        val obj = JsonObject()

        var nextToken = this.next()
        while (nextToken.type != JsonToken.Type.END_OBJECT) {
            if (obj.children.isNotEmpty()) {
                nextToken.checkType(JsonToken.Type.COMMA)
                nextToken = this.next()
            }

            nextToken.checkType(JsonToken.Type.STRING)
            val key = nextToken.value.toString()

            this.next().checkType(JsonToken.Type.COLON)

            obj.add(key, this.parseJsonElement())

            nextToken = this.next()
        }

        return obj
    }

    private fun Iterator<JsonToken>.parseJsonElement(startToken: JsonToken? = null): JsonElement {
        val token = startToken ?: this.next()
        return when (token.type) {
            JsonToken.Type.BEGIN_ARRAY -> this.parseJsonArray()
            JsonToken.Type.BEGIN_OBJECT -> this.parseJsonObject()
            JsonToken.Type.BOOLEAN,
            JsonToken.Type.STRING,
            JsonToken.Type.NUMBER,
            JsonToken.Type.NULL -> JsonProperty(token.value)
            else -> throw JsonParseException("Unsupported token: $token")
        }
    }

    private fun JsonToken.checkType(expectedType: JsonToken.Type) {
        if (type != expectedType) {
            throw JsonParseException("Unexpected type, expected: $expectedType, actual: $this")
        }
    }

    private fun JsonElement.toDto(): CityDto {
        check(this is JsonObject)
        return CityDto(
            id = ((this.children["id"] as JsonProperty).value as Number).toInt(),
            name = (this.children["name"] as JsonProperty).value as String
        )
    }
}