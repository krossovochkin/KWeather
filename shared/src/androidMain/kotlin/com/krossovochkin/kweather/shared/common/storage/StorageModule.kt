package com.krossovochkin.kweather.shared.common.storage

import android.content.Context
import com.krossovochkin.kweather.AppDatabase
import com.krossovochkin.kweather.shared.common.storage.citylist.DbCityListDatasource
import com.krossovochkin.kweather.shared.common.storage.citylist.FileCityListDatasource
import com.squareup.sqldelight.android.AndroidSqliteDriver

private const val APP_DB_NAME = "kweather.db"

actual class StorageModule(
    private val context: Context
) {

    actual val storage: Storage
        get() = StorageImpl(context)

    actual val fileCityListDatasource: FileCityListDatasource
        get() = FileCityListDatasource(context)

    actual val dbCityListDatasource: DbCityListDatasource
        get() = DbCityListDatasource(appDatabase)

    private val appDatabase: AppDatabase by lazy {
        AppDatabase(AndroidSqliteDriver(AppDatabase.Schema, context, APP_DB_NAME))
    }
}