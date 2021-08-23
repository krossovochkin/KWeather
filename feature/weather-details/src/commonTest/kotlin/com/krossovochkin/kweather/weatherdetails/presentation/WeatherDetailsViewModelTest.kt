package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.test.runBlockingTest
import com.krossovochkin.localization.test.TestLocalizationManager
import com.krossovochkin.location.test.TestLocationProvider
import com.krossovochkin.navigation.test.TestRouter
import com.krossovochkin.storagecurrentcity.test.TestCurrentCityStorage
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.TestWeatherDetailsRepository
import com.krossovochkin.kweather.weatherdetails.presentation.localization.LocalizedStringKey
import kotlin.test.Test

class WeatherDetailsViewModelTest {

    private val currentCityStorage = TestCurrentCityStorage()
    private val currentCityInteractor: GetCurrentCityInteractor =
        GetCurrentCityInteractorImpl(
            currentCityStorage = currentCityStorage
        )

    private val weatherDetailsRepository = TestWeatherDetailsRepository()
    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor =
        GetWeatherDetailsInteractorImpl(
            weatherDetailsRepository = weatherDetailsRepository
        )

    private val router = TestRouter()
    private val localizationManager = TestLocalizationManager<LocalizedStringKey>()
    private val locationProvider = TestLocationProvider()

    private lateinit var viewModel: WeatherDetailsViewModel

    @Test
    fun test() {
        viewModel = WeatherDetailsViewModelImpl(
            getWeatherDetailsInteractor = getWeatherDetailsInteractor,
            getCurrentCityInteractor = currentCityInteractor,
            router = router,
            localizationManager = localizationManager,
            locationProvider = locationProvider
        )

        runBlockingTest {
            viewModel.observeState() // TODO
        }
    }
}
