package com.krossovochkin.kweather.shared.feature.citylist.data

interface CityListStorage {

    suspend fun getCityListData(): String
}

expect class CityListStorageImpl : CityListStorage
