package com.krossovochkin.kweather.core.storage

import com.krossovochkin.kweather.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

private const val APP_DB_NAME = "kweather.db"

actual val storageModule = DI.Module("StorageModule") {
    bind<Storage>() with singleton { StorageImpl(instance()) }

    bind<AppDatabase>() with singleton {
        AppDatabase(AndroidSqliteDriver(AppDatabase.Schema, instance(), APP_DB_NAME))
    }

    bind<MutableCurrentCityIdStorage>() with singleton {
        CurrentCityIdStorageImpl(
            storage = instance()
        )
    }

    bind<CurrentCityIdStorage>() with singleton { instance<MutableCurrentCityIdStorage>() }
}
