package com.krossovochkin.navigation

interface Router {

    suspend fun navigateTo(destination: RouterDestination)
}
