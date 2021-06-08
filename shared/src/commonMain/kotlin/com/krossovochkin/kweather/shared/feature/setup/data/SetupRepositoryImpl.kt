package com.krossovochkin.kweather.shared.feature.setup.data

import com.krossovochkin.kweather.shared.feature.setup.domain.SetupRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class SetupRepositoryImpl(
    private val cityListProvider: FileCityListProvider,
    private val cityListInitializer: DbCityListInitializer
) : SetupRepository {

    override suspend fun setup() {
        if (cityListInitializer.isInitialized) {
            return
        }

        cityListProvider.observe()
            .onStart { cityListInitializer.startSetup() }
            .onEach { cityDto -> cityListInitializer.insertCity(cityDto) }
            .onCompletion { cause: Throwable? ->
                if (cause != null) {
                    cityListInitializer.rollbackSetup()
                } else {
                    cityListInitializer.endSetup()
                }
            }
            .collect()
    }
}
