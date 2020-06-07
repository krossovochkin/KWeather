package com.krossovochkin.kweather.shared.common.storage.citylist

import com.krossovochkin.kweather.AppDatabase
import com.krossovochkin.kweather.CitiesQueries
import com.krossovochkin.kweather.shared.common.storage.CityDto

class DbCityListDatasource(
    db: AppDatabase
) : CityListDatasource {

    private val dao: CitiesQueries = db.citiesQueries
    private val mapper = { id: Long, name: String -> CityDto(id.toInt(), name) }
    private val contains = { query: String -> "%$query%" }

    override suspend fun getCityList(query: String): List<CityDto> {
        return if (query.isEmpty()) {
            dao.selectAll(mapper).executeAsList()
        } else {
            dao.selectByQuery(name = contains(query), mapper = mapper).executeAsList()
        }
    }

    override suspend fun getCityList(query: String, limit: Int): List<CityDto> {
        return if (query.isEmpty()) {
            dao.selectAllLimit(value = limit.toLong(), mapper = mapper).executeAsList()
        } else {
            dao.selectByQueryLimit(name = contains(query), value = limit.toLong(), mapper = mapper)
                .executeAsList()
        }
    }

    override suspend fun setCityList(cityList: List<CityDto>) {
        dao.transaction {
            dao.deleteAll()
            cityList.forEach { city ->
                dao.insert(city.id.toLong(), city.name)
            }
        }
    }
}
