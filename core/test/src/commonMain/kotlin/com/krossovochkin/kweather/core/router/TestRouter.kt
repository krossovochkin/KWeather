package com.krossovochkin.kweather.core.router

class TestRouter : Router {

    var currentDestination: RouterDestination? = null

    override fun navigateTo(destination: RouterDestination) {
        currentDestination = destination
    }
}
