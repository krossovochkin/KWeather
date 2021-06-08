package com.krossovochkin.kweather.shared.feature.setup.data.json

import java.io.InputStream

internal class CharInputStreamIterator(
    private val input: InputStream
) : CharIterator() {

    private enum class State {
        Ready,
        NotReady,
        Done,
        Failed
    }

    private var state = State.NotReady
    private var nextValue: Char? = null

    override fun hasNext(): Boolean {
        require(state != State.Failed)
        return when (state) {
            State.Done -> false
            State.Ready -> true
            else -> tryToComputeNext()
        }
    }

    override fun nextChar(): Char {
        if (!hasNext()) throw NoSuchElementException()
        state = State.NotReady
        @Suppress("UNCHECKED_CAST")
        return nextValue!!
    }

    private fun tryToComputeNext(): Boolean {
        state = State.Failed
        computeNext()
        return state == State.Ready
    }

    private fun computeNext() {
        val char = input.read()
        return if (char != -1) {
            nextValue = char.toChar()
            state = State.Ready
        } else {
            state = State.Done
        }
    }
}

fun InputStream.iterator(): CharIterator {
    return CharInputStreamIterator(this)
}