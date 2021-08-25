package com.krossovochkin.kweather.navigation

import com.krossovochkin.navigation.AndroidRouter
import com.krossovochkin.navigation.Router
import org.kodein.di.DirectDIAware
import org.kodein.di.instance

internal actual object NavigationModuleFactory {

    actual fun createRouter(directDIAware: DirectDIAware): Router<RouterDestination> {
        return AndroidRouter(
            navController = directDIAware.instance()
        )
    }
}
