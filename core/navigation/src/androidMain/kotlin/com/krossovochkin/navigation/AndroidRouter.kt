package com.krossovochkin.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

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

fun routerModule(navController: NavController) = DI.Module("RouterModule") {
    bind<Router>() with singleton { AndroidRouter(navController) }
}
