package com.krossovochkin.kweather.core.router

class TestRouter : com.krossovochkin.navigation.Router {

    var currentDestination: com.krossovochkin.navigation.RouterDestination? = null

    override suspend fun navigateTo(destination: com.krossovochkin.navigation.RouterDestination) {
        currentDestination = destination
    }
}
