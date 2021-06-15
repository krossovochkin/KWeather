package com.krossovochkin.kweather.setup.data.json

internal class JsonCharIterator(
    private val iterator: CharIterator
) {
    var current: Char? = iterator.next()
        private set

    var isDone: Boolean = false
        private set

    fun hasNext(): Boolean = !isDone

    fun moveNext() {
        if (iterator.hasNext()) {
            current = iterator.next()
        } else {
            current = null
            isDone = true
        }
    }
}