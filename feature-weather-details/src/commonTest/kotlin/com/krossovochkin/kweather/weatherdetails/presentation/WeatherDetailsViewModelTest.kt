package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.core.test.runBlockingTest
import com.krossovochkin.kweather.core.localization.TestLocalizationManager
import com.krossovochkin.kweather.core.router.TestRouter
import com.krossovochkin.kweather.core.storage.TestCurrentCityIdStorage
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityIdInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityIdInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.TestWeatherDetailsRepository
import com.krossovochkin.kweather.weatherdetails.presentation.localization.LocalizedStringKey
import kotlin.test.Test

class WeatherDetailsViewModelTest {

    private val currentCityIdStorage = TestCurrentCityIdStorage()
    private val currentCityIdInteractor: GetCurrentCityIdInteractor =
        GetCurrentCityIdInteractorImpl(
            currentCityIdStorage = currentCityIdStorage
        )

    private val weatherDetailsRepository = TestWeatherDetailsRepository()
    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor =
        GetWeatherDetailsInteractorImpl(
            weatherDetailsRepository = weatherDetailsRepository
        )

    private val router = TestRouter()
    private val localizationManager = TestLocalizationManager<LocalizedStringKey>()

    private lateinit var viewModel: WeatherDetailsViewModel

    @Test
    fun test() {
        viewModel = WeatherDetailsViewModelImpl(
            getWeatherDetailsInteractor = getWeatherDetailsInteractor,
            getCurrentCityIdInteractor = currentCityIdInteractor,
            router = router,
            localizationManager = localizationManager
        )

        runBlockingTest {
            viewModel.observeState() // TODO
        }
    }
}
