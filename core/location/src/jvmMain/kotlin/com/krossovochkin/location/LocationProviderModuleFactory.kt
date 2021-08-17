package com.krossovochkin.location

import org.kodein.di.bindings.NoArgBindingDI

actual object LocationProviderModuleFactory {

    actual fun createLocationProviderImpl(noArgBindingDI: NoArgBindingDI<Any>): LocationProvider {
        return LocationProviderImpl()
    }
}
