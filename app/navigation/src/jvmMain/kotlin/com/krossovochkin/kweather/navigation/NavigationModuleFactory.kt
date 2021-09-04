package com.krossovochkin.kweather.navigation

import com.krossovochkin.navigation.Router
import com.krossovochkin.navigation.SimpleRouter
import org.kodein.di.DirectDIAware

internal actual object NavigationModuleFactory {

    actual fun createRouter(directDIAware: DirectDIAware): Router<RouterDestination> {
        return SimpleRouter()
    }
}
