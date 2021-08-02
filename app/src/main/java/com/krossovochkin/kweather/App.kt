package com.krossovochkin.kweather

import android.app.Application
import com.krossovochkin.kweather.core.networkModule
import com.krossovochkin.lifecycle.Lifecycle
import com.krossovochkin.permission.PermissionManager
import com.krossovochkin.storage.storageModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class App : Application(), DIAware {

    override val di: DI = DI.lazy {
        import(appModule(this@App))
        import(storageModule)
        import(networkModule)
    }

    private val lifecycle: Lifecycle by di.instance()
    private val permissionManager: PermissionManager by di.instance()

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            permissionManager.init()
        }
        lifecycle.init()
    }
}
