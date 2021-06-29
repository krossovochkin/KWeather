package com.krossovochkin.navigation

import org.kodein.di.DirectDIAware

internal expect object NavigationModuleFactory {

    fun createRouter(directDIAware: DirectDIAware): Router
}
