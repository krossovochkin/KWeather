package com.krossovochkin.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class RouterImpl : Router {

    private var currentDestinationStateFlow =
        MutableStateFlow<RouterDestination?>(null)

    override fun observeDestination(): Flow<RouterDestination> {
        return currentDestinationStateFlow
            .filterNotNull()
    }

    override suspend fun navigateTo(destination: RouterDestination) {
        currentDestinationStateFlow.emit(destination)
    }
}
