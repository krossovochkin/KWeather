package com.krossovochkin.navigation.test

import com.krossovochkin.navigation.Router
import com.krossovochkin.navigation.RouterDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class TestRouter<RouterDestinationT : RouterDestination> : Router<RouterDestinationT> {

    private var currentDestinationStateFlow =
        MutableStateFlow<RouterDestinationT?>(null)

    override fun observeDestination(): Flow<RouterDestinationT> {
        return currentDestinationStateFlow.filterNotNull()
    }

    override suspend fun navigateTo(destination: RouterDestinationT) {
        currentDestinationStateFlow.emit(destination)
    }
}
