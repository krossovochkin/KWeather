package com.krossovochkin.kweather

import android.app.Application
import android.util.Log
import com.krossovochkin.kweather.network.networkModule
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
import java.io.File
import java.lang.System
import java.lang.Thread
import kotlin.io.writeText

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
        initCrashHandler()
        applicationScope.launch {
            permissionManager.init()
        }
        lifecycle.init()
    }

    private fun initCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            val message = e.message + "\n" +
                Log.getStackTraceString(e)
            val file = File(externalCacheDir, "${System.currentTimeMillis()}")
            file.writeText(message)
        }
    }
}
