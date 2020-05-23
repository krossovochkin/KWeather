package com.krossovochkin.kweather.shared.common.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ViewModel<StateT, ActionT> {

    fun observeState(): Flow<StateT>

    fun performAction(action: ActionT)

    fun dispose() = Unit
}
