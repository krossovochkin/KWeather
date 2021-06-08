package com.krossovochkin.kweather.shared.feature.setup.data.json

import com.krossovochkin.kweather.shared.feature.setup.data.json.JsonToken.Type
import java.io.InputStream

class JsonParser {

    @Throws(JsonParseException::class)
    fun parse(text: String): JsonElement {
        return parse(text.iterator())
    }

    @Throws(JsonParseException::class)
    fun parse(input: InputStream): JsonElement {
        return parse(input.iterator())
    }

    private fun parse(text: CharIterator): JsonElement {
        return runCatching {
            val tokenizer = JsonTokenizer(JsonCharIterator(text))
            tokenizer.parseJsonElement()
                .also { tokenizer.validateCompleted() }
        }.getOrElse { error ->
            throw JsonParseException(error.message.orEmpty(), error)
        }
    }

    private fun Iterator<JsonToken>.parseJsonElement(startToken: JsonToken? = null): JsonElement {
        val token = startToken ?: this.next()
        return when (token.type) {
            Type.BEGIN_ARRAY -> this.parseJsonArray()
            Type.BEGIN_OBJECT -> this.parseJsonObject()
            Type.BOOLEAN,
            Type.STRING,
            Type.NUMBER,
            Type.NULL -> JsonProperty(token.value)
            else -> throw JsonParseException("Unsupported token: $token")
        }
    }

    private fun Iterator<JsonToken>.parseJsonObject(): JsonObject {
        val obj = JsonObject()

        var nextToken = this.next()
        while (nextToken.type != Type.END_OBJECT) {
            if (obj.children.isNotEmpty()) {
                nextToken.checkType(Type.COMMA)
                nextToken = this.next()
            }

            nextToken.checkType(Type.STRING)
            val key = nextToken.value.toString()

            this.next().checkType(Type.COLON)

            obj.add(key, this.parseJsonElement())

            nextToken = this.next()
        }

        return obj
    }

    private fun Iterator<JsonToken>.parseJsonArray(): JsonArray {
        val array = JsonArray()

        var nextToken = this.next()
        while (nextToken.type != Type.END_ARRAY) {
            if (array.children.isNotEmpty()) {
                nextToken.checkType(Type.COMMA)
                nextToken = this.next()
            }

            array.add(this.parseJsonElement(startToken = nextToken))

            nextToken = this.next()
        }

        return array
    }

    private fun JsonToken.checkType(expectedType: Type) {
        if (type != expectedType) {
            throw JsonParseException("Unexpected type, expected: $expectedType, actual: $this")
        }
    }
}