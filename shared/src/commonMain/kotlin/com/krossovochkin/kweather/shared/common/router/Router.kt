package com.krossovochkin.kweather.shared.common.router

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface Router {

    val currentDestination: RouterDestination

    val observeCurrentDestination: Flow<RouterDestination>

    fun navigateTo(destination: RouterDestination)
}

class RouterImpl : Router {

    private val current = MutableStateFlow<RouterDestination>(RouterDestination.WeatherDetails)

    override val observeCurrentDestination: Flow<RouterDestination> = current

    override val currentDestination: RouterDestination
        get() = current.value

    override fun navigateTo(destination: RouterDestination) {
        current.value = destination
    }
}