package com.krossovochkin.location

import org.kodein.di.bindings.NoArgBindingDI

expect object LocationProviderModuleFactory {

    fun createLocationProviderImpl(noArgBindingDI: NoArgBindingDI<Any>): LocationProvider
}
