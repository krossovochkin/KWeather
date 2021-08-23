package com.krossovochkin.presentation

import kotlinx.coroutines.flow.Flow

interface ViewModel<StateT, ActionT> {

    fun observeState(): Flow<StateT>

    fun performAction(action: ActionT)

    fun dispose() = Unit
}
