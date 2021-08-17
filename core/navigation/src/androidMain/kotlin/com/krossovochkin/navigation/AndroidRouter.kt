package com.krossovochkin.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AndroidRouter(
    private val navController: NavController
) : Router {

    override fun observeDestination(): Flow<RouterDestination> {
        return navController.currentBackStackEntryFlow
            .map { it.destination.route }
            .map { route ->
                when (route) {
                    RouterDestination.CityList.route -> RouterDestination.CityList
                    RouterDestination.WeatherDetails.route -> RouterDestination.WeatherDetails
                    else -> null
                }
            }
            .filterNotNull()
    }

    override suspend fun navigateTo(destination: RouterDestination) =
        withContext(Dispatchers.Main.immediate) {
            val currentDestinationId = navController.currentDestination?.id
            if (currentDestinationId != null) {
                navController.popBackStack(currentDestinationId, true)
            }
            navController.navigate(destination.route)
        }
}
