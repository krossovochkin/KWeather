package com.krossovochkin.kweather.core.router

interface Router {

    suspend fun navigateTo(destination: RouterDestination)
}
