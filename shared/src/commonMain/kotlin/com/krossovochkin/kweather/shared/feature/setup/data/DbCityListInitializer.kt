package com.krossovochkin.kweather.shared.feature.setup.data

import com.krossovochkin.kweather.AppDatabase
import com.krossovochkin.kweather.CitiesQueries
import com.krossovochkin.kweather.shared.common.storage.CityDto

class DbCityListInitializer(
    db: AppDatabase
) {
    private val dao: CitiesQueries = db.citiesQueries

    val isInitialized: Boolean
        get() = dao.selectByQueryLimit("", 1).executeAsList().isNotEmpty()

    fun startSetup() {
        dao.beginTransaction()
        dao.deleteAll()
    }

    fun insertCity(city: CityDto) {
        dao.insert(city.id.toLong(), city.name)
    }

    fun endSetup() {
        dao.commitTransaction()
    }

    fun rollbackSetup() {
        dao.rollbackTransaction()
    }
}