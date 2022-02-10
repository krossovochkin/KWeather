package com.krossovochkin.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

abstract class BaseViewModel<StateT, ActionT, ActionResultT>(
    initialState: StateT,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel<StateT, ActionT> {

    private val actionResults = MutableSharedFlow<ActionResultT>(
        replay = 0,
        extraBufferCapacity = 64
    )
    private val stateFlow = MutableStateFlow(initialState)

    protected val scope: CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
    protected val state: StateT
        get() = stateFlow.value

    init {
        actionResults
            .scan(initialState, ::reduceState)
            .onEach { stateFlow.value = it }
            .launchIn(scope)
    }

    protected abstract suspend fun reduceState(
        state: StateT,
        result: ActionResultT
    ): StateT

    protected suspend fun onActionResult(result: ActionResultT) {
        actionResults.emit(result)
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
