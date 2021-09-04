package com.krossovochkin.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class SimpleRouter<RouterDestinationT : RouterDestination> : Router<RouterDestinationT> {

    private val backstack = ArrayDeque<RouterDestinationT>()
    private var currentDestinationStateFlow =
        MutableStateFlow<RouterDestinationT?>(null)

    override fun observeDestination(): Flow<RouterDestinationT> {
        return currentDestinationStateFlow
            .filterNotNull()
    }

    override suspend fun navigateTo(destination: RouterDestinationT) {
        backstack.addFirst(destination)
        currentDestinationStateFlow.emit(destination)
    }

    override suspend fun navigateBack(): Boolean {
        return if (backstack.size > 1) {
            backstack.removeFirst()
            currentDestinationStateFlow.emit(backstack.first())
            true
        } else {
            false
        }
    }
}
