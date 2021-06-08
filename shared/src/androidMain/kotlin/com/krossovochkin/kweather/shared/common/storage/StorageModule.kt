package com.krossovochkin.kweather.shared.common.storage

import com.krossovochkin.kweather.AppDatabase
import com.krossovochkin.kweather.shared.common.storage.citylist.CityListDatasource
import com.krossovochkin.kweather.shared.common.storage.citylist.DbCityListDatasource
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapper
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapperImpl
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
    bind<CityListDatasource>(tag = DbCityListDatasource::class) with singleton {
        DbCityListDatasource(instance())
    }

    bind<MutableCurrentCityStorage>() with singleton {
        CurrentCityStorageImpl(
            storage = instance(),
            cityListMapper = instance()
        )
    }

    bind<CurrentCityStorage>() with singleton { instance<MutableCurrentCityStorage>() }

    bind<CityListMapper>() with singleton {
        CityListMapperImpl()
    }
}
