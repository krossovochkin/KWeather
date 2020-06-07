package com.krossovochkin.kweather.shared.common.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

abstract class BaseViewModel<StateT, ActionT, ActionResultT>(
    initialState: StateT
) : ViewModel<StateT, ActionT> {

    private val actionResults = BroadcastChannel<ActionResultT>(Channel.BUFFERED)
    private val stateFlow = MutableStateFlow(initialState)

    protected val scope: CoroutineScope = CoroutineScope(SupervisorJob())
    protected val state: StateT
        get() = stateFlow.value

    init {
        actionResults
            .asFlow()
            .scan(initialState, ::reduceState)
            .onEach { stateFlow.value = it }
            .launchIn(scope)
    }

    protected abstract suspend fun reduceState(
        state: StateT,
        result: ActionResultT
    ): StateT

    protected suspend fun onActionResult(result: ActionResultT) {
        actionResults.send(result)
    }

    final override fun observeState(): Flow<StateT> {
        return stateFlow
    }

    abstract override fun performAction(action: ActionT)

    final override fun dispose() {
        scope.cancel()
    }

    protected fun reducerError(state: StateT, result: ActionResultT): Nothing {
        error("Can't apply result: $result to state: $state")
    }
}
