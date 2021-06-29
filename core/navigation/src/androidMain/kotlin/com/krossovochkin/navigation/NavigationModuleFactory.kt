package com.krossovochkin.navigation

import org.kodein.di.DirectDIAware
import org.kodein.di.instance

internal actual object NavigationModuleFactory {

    actual fun createRouter(directDIAware: DirectDIAware): Router {
        return AndroidRouter(
            navController = directDIAware.instance()
        )
    }
}
