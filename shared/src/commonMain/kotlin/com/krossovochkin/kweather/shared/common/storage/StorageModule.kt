package com.krossovochkin.kweather.shared.common.storage

import com.krossovochkin.kweather.shared.feature.citylist.data.CityListStorage

expect class StorageModule {

    val storage: Storage

    val cityListStorage: CityListStorage
}