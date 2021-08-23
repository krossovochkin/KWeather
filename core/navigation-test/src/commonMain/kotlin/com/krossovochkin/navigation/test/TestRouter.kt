package com.krossovochkin.navigation.test

import com.krossovochkin.navigation.RouterDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestRouter : com.krossovochkin.navigation.Router {

    private var currentDestinationStateFlow =
        MutableStateFlow<RouterDestination>(RouterDestination.WeatherDetails)

    override fun observeDestination(): Flow<RouterDestination> {
        return currentDestinationStateFlow
    }

    override suspend fun navigateTo(destination: RouterDestination) {
        currentDestinationStateFlow.emit(destination)
    }
}
