package com.krossovochkin.navigation

import org.kodein.di.DirectDIAware

internal actual object NavigationModuleFactory {

    actual fun createRouter(directDIAware: DirectDIAware): Router {
        return RouterImpl()
    }
}
