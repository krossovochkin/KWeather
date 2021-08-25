package com.krossovochkin.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class RouterImpl<RouterDestinationT : RouterDestination> : Router<RouterDestinationT> {

    private var currentDestinationStateFlow =
        MutableStateFlow<RouterDestinationT?>(null)

    override fun observeDestination(): Flow<RouterDestinationT> {
        return currentDestinationStateFlow
            .filterNotNull()
    }

    override suspend fun navigateTo(destination: RouterDestinationT) {
        currentDestinationStateFlow.emit(destination)
    }
}
