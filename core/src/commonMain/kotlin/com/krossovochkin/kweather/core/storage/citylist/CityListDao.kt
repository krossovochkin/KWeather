package com.krossovochkin.kweather.core.storage.citylist

import com.krossovochkin.kweather.AppDatabase
import com.krossovochkin.kweather.Cities

class CityListDao(
    db: AppDatabase
) {

    private val dao = db.citiesQueries

    fun selectAllLimit(limit: Int): List<Cities> {
        return dao.selectAllLimit(limit.toLong()).executeAsList()
    }

    fun beginTransaction() {
        dao.beginTransaction()
    }

    fun deleteAll() {
        dao.deleteAll()
    }

    fun insert(id: Int, name: String) {
        dao.insert(id.toLong(), name)
    }

    fun commitTransaction() {
        dao.commitTransaction()
    }

    fun rollbackTransaction() {
        dao.rollbackTransaction()
    }
}