package com.krossovochkin.location

import org.kodein.di.bindings.NoArgBindingDI
import org.kodein.di.instance

actual object LocationProviderModuleFactory {

    actual fun createLocationProviderImpl(noArgBindingDI: NoArgBindingDI<Any>): LocationProvider {
        return LocationProviderImpl(
            permissionManager = noArgBindingDI.instance(),
            lifecycle = noArgBindingDI.instance()
        )
    }
}
