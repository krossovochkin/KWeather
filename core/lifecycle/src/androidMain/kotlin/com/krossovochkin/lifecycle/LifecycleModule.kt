package com.krossovochkin.lifecycle

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val lifecycleModule = DI.Module("LifecycleModule") {

    bind<Lifecycle>() with singleton {
        LifecycleImpl(
            application = instance()
        )
    }
}
