package com.krossovochkin.navigation

import kotlinx.coroutines.flow.Flow

interface Router<RouterDestinationT : RouterDestination> {

    fun observeDestination(): Flow<RouterDestinationT>

    suspend fun navigateTo(destination: RouterDestinationT)

    suspend fun navigateBack(): Boolean
}
