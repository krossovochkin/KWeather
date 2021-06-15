package com.krossovochkin.kweather.setup.data

import com.krossovochkin.kweather.core.storage.CityDto
import com.krossovochkin.kweather.core.storage.citylist.CityListDao

class DbCityListInitializer(
    private val dao: CityListDao
) {
    val isInitialized: Boolean
        get() = dao.selectByQueryLimit("", 1).isNotEmpty()

    fun startSetup() {
        dao.beginTransaction()
        dao.deleteAll()
    }

    fun insertCity(city: CityDto) {
        dao.insert(city.id, city.name)
    }

    fun endSetup() {
        dao.commitTransaction()
    }

    fun rollbackSetup() {
        dao.rollbackTransaction()
    }
}