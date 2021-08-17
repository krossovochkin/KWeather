package com.krossovochkin.navigation

import kotlinx.coroutines.flow.Flow

interface Router {

    fun observeDestination(): Flow<RouterDestination>

    suspend fun navigateTo(destination: RouterDestination)
}
