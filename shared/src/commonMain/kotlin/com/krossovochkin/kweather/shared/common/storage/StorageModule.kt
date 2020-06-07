package com.krossovochkin.kweather.shared.common.storage

import com.krossovochkin.kweather.shared.common.storage.citylist.DbCityListDatasource
import com.krossovochkin.kweather.shared.common.storage.citylist.FileCityListDatasource

expect class StorageModule {

    val storage: Storage

    val fileCityListDatasource: FileCityListDatasource

    val dbCityListDatasource: DbCityListDatasource
}