package com.krossovochkin.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidRouter(
    private val navController: NavController
) : Router {

    override suspend fun navigateTo(destination: RouterDestination) =
        withContext(Dispatchers.Main.immediate) {
            val currentDestinationId = navController.currentDestination?.id
            if (currentDestinationId != null) {
                navController.popBackStack(currentDestinationId, true)
            }
            navController.navigate(destination.route)
        }
}
