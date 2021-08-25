package com.krossovochkin.kweather.navigation

import com.krossovochkin.navigation.Router
import org.kodein.di.DirectDIAware

internal expect object NavigationModuleFactory {

    fun createRouter(directDIAware: DirectDIAware): Router<RouterDestination>
}
