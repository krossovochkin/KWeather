package com.krossovochkin.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AndroidRouter<RouterDestinationT : RouterDestination>(
    private val navController: NavController
) : Router<RouterDestinationT> {

    private val routerDestinationMap = mutableMapOf<String, RouterDestinationT>()

    override fun observeDestination(): Flow<RouterDestinationT> {
        return navController.currentBackStackEntryFlow
            .map { it.destination.route }
            .map { route -> routerDestinationMap[route] }
            .filterNotNull()
    }

    override suspend fun navigateTo(destination: RouterDestinationT) =
        withContext(Dispatchers.Main.immediate) {
            routerDestinationMap[destination.route] = destination
            navController.navigate(destination.route)
        }

    override suspend fun navigateBack() =
        withContext(Dispatchers.Main.immediate) {
            navController.popBackStack()
        }
}
