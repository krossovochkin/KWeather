package com.krossovochkin.kweather.setup.data.json

import com.krossovochkin.kweather.setup.data.json.JsonToken.Type

internal class JsonTokenizer(
    private val iterator: JsonCharIterator
) : AbstractIterator<JsonToken>() {

    override fun computeNext() {
        if (iterator.isDone) {
            done()
        } else {
            getNext()?.let(::setNext)
        }
    }

    private fun getNext(): JsonToken? {
        val type = detect(iterator.current) ?: return null
        val token = scan(type, iterator)

        return if (token.type == Type.WHITESPACE) {
            getNext()
        } else {
            token
        }
    }

    fun validateCompleted() {
        while (hasNext()) {
            val token = next()
            if (token.type != Type.WHITESPACE) {
                throw JsonParseException("Error parsing, non-whitespace token after json: $token")
            }
        }
    }

    private fun detect(char: Char?): Type? {
        return when {
            char == null -> null
            char == '{' -> Type.BEGIN_OBJECT
            char == '}' -> Type.END_OBJECT
            char == '[' -> Type.BEGIN_ARRAY
            char == ']' -> Type.END_ARRAY
            char == ':' -> Type.COLON
            char == ',' -> Type.COMMA
            char == '"' -> Type.STRING
            char == 't' || char == 'f' -> Type.BOOLEAN
            char == 'n' -> Type.NULL
            char == '+' || char == '-' || char.isDigit() -> Type.NUMBER
            WHITESPACES_REGEX.matches(char.toString()) -> Type.WHITESPACE
            else -> throw JsonParseException("Error detecting, unknown char: $char")
        }
    }

    private fun scan(type: Type, iterator: JsonCharIterator): JsonToken {
        return when (type) {
            Type.WHITESPACE -> JsonToken(Type.WHITESPACE, iterator.readWhitespaces())
            Type.BEGIN_ARRAY,
            Type.END_ARRAY,
            Type.BEGIN_OBJECT,
            Type.END_OBJECT,
            Type.COLON,
            Type.COMMA -> JsonToken(type, iterator.readChar())
            Type.BOOLEAN -> JsonToken(Type.BOOLEAN, iterator.readBoolean())
            Type.NULL -> JsonToken(Type.NULL, iterator.readNull())
            Type.STRING -> JsonToken(Type.STRING, iterator.readString())
            Type.NUMBER -> JsonToken(Type.NUMBER, iterator.readNumber())
        }
    }

    private fun JsonCharIterator.readChar(): String {
        return current.toString()
            .also { moveNext() }
    }

    private fun JsonCharIterator.readBoolean(): Boolean {
        return when (val c = current) {
            't' -> {
                readWord("true")
                true
            }
            'f' -> {
                readWord("false")
                false
            }
            else -> throw JsonParseException("Error reading Boolean, unknown char: $c")
        }
    }

    private fun JsonCharIterator.readNull(): Any? {
        readWord("null")
        return null
    }

    private fun JsonCharIterator.readWhitespaces(): String {
        return buildString {
            append(current)
            while (hasNext()) {
                moveNext()
                if (WHITESPACES_REGEX.matches(current.toString())) {
                    append(current)
                } else {
                    break
                }
            }
        }
    }

    private fun JsonCharIterator.readString(): String {
        return buildString {
            // do not append start "
            // append(current)
            while (hasNext()) {
                moveNext()
                if (current != '\"') {
                    if (current == '\\') {
                        append(current)
                        moveNext()
                        if (current == '\"' || current == '\\' || current == '/' || current == 'b' ||
                                current == 'f' || current == 'n' || current == 'r' || current == 't' ||
                                current == 'u') {
                            if (current == '/') {
                                setLength(this.length - 1)
                            }
                            append(current)
                            continue
                        } else {
                            throw JsonParseException("Unsupported char $current")
                        }
                    }
                    append(current)
                } else {
                    moveNext() // skip end "
                    break
                }
            }
        }
    }

    private fun JsonCharIterator.readNumber(): Number {
        var hadFraction = false
        var hadExp = false
        var hadExpSign = false

        return buildString {
            // implies sign was checked
            append(current)
            while (hasNext()) {
                moveNext()
                if (current == '-' || current == '+') {
                    if (hadExp && !hadExpSign) {
                        hadExpSign = true
                        append(current)
                        continue
                    } else {
                        throw JsonParseException("Unexpected exponential sign")
                    }
                }
                if (current == '.') {
                    if (!hadFraction) {
                        hadFraction = true
                        append(current)
                        continue
                    } else {
                        throw JsonParseException("Unexpected fraction")
                    }
                }
                if (current == 'e' || current == 'E') {
                    if (!hadExp) {
                        hadExp = true
                        append(current)
                        continue
                    } else {
                        throw JsonParseException("Unexpected exponential")
                    }
                }

                if (current?.isDigit() == true) {
                    append(current)
                } else {
                    break
                }
            }
        }.toBigDecimal()
    }

    private fun JsonCharIterator.readWord(word: String) {
        word.forEach { c ->
            if (current != c) {
                throw JsonParseException("Error parsing word $word, expected: $current, actual: $c")
            }
            moveNext()
        }
    }

    companion object {
        private val WHITESPACES_REGEX = "[\u0020\u000A\u000D\u0009]".toRegex()
    }
}