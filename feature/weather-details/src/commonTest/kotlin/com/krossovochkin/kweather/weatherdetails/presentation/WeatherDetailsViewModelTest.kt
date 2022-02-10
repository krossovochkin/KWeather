package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.kweather.domain.test.TestCityBuilder
import com.krossovochkin.kweather.navigation.RouterDestination
import com.krossovochkin.kweather.storagecurrentcity.test.TestCurrentCityStorage
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_TEMPERATURE
import com.krossovochkin.kweather.weatherdetails.domain.TestWeatherDetailsBuilder
import com.krossovochkin.kweather.weatherdetails.domain.TestWeatherDetailsRepository
import com.krossovochkin.kweather.weatherdetails.presentation.localization.LocalizedStringKey
import com.krossovochkin.localization.test.TestLocalizationManager
import com.krossovochkin.location.test.TestLocationProvider
import com.krossovochkin.navigation.SimpleRouter
import com.krossovochkin.test.test
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class WeatherDetailsViewModelTest {

    private val currentCityStorage = TestCurrentCityStorage(DELAY_MILLIS_CITY_LOAD)
    private val currentCityInteractor: GetCurrentCityInteractor =
        GetCurrentCityInteractorImpl(
            currentCityStorage = currentCityStorage
        )

    private val weatherDetailsRepository = TestWeatherDetailsRepository(DELAY_MILLIS_API_LOAD)
    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor =
        GetWeatherDetailsInteractorImpl(
            weatherDetailsRepository = weatherDetailsRepository
        )

    private val router = SimpleRouter<RouterDestination>()
    private val localizationManager = TestLocalizationManager<LocalizedStringKey>().apply {
        put(LocalizedStringKey.WeatherDetails_ChangeCityText, TEXT_CHANGE_CITY)
        put(LocalizedStringKey.WeatherDetails_CityMissingMessage, TEXT_CITY_MISSING_MESSAGE)
        put(LocalizedStringKey.WeatherDetails_SelectCity, TEXT_SELECT_CITY)
        put(
            LocalizedStringKey.WeatherDetails_WeatherConditionsImageContentDescription,
            TEXT_WEATHER_CONDITIONS_IMAGE_CONTENT_DESCRIPTION
        )
        put(
            LocalizedStringKey.WeatherDetails_TemperatureDay,
            listOf("$DEFAULT_TEMPERATURE$CELSIUS_DEGREES"),
            TEXT_TEMPERATURE_DAY
        )
        put(
            LocalizedStringKey.WeatherDetails_TemperatureNight,
            listOf("$DEFAULT_TEMPERATURE$CELSIUS_DEGREES"),
            TEXT_TEMPERATURE_NIGHT
        )
        put(
            LocalizedStringKey.WeatherDetails_TemperatureFeelsLike,
            listOf("$DEFAULT_TEMPERATURE$CELSIUS_DEGREES"),
            TEXT_TEMPERATURE_FEELS_LIKE
        )
    }
    private val scheduler = TestCoroutineScheduler()
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(scheduler)
    private val locationProvider = TestLocationProvider()

    private val viewModel: WeatherDetailsViewModel = WeatherDetailsViewModelImpl(
        getWeatherDetailsInteractor = getWeatherDetailsInteractor,
        getCurrentCityInteractor = currentCityInteractor,
        router = router,
        localizationManager = localizationManager,
        locationProvider = locationProvider,
        defaultDispatcher = dispatcher
    )

    @Test
    fun `on start emits loading`() = runTest(dispatcher) {
        val observer = viewModel.observeState().test(this)

        observer
            .assertValueCount(1)
            .assertLatestValue(
                WeatherDetailsState.Loading
            )
            .finish()
    }

    @Test
    fun `on start if no city selected emits unknown city error`() = runTest(dispatcher) {
        val observer = viewModel.observeState().test(this)

        advanceTimeBy(DELAY_MILLIS_CITY_LOAD)
        runCurrent()

        observer
            .assertValueCount(2)
            .assertLatestValue(
                WeatherDetailsState.CityUnknownError(
                    cityMissingMessageText = TEXT_CITY_MISSING_MESSAGE,
                    selectCityButtonText = TEXT_SELECT_CITY
                )
            )
            .finish()
    }

    @Test
    fun `on start if city selected and get weather details success emits data state`() =
        runTest(dispatcher) {
            val city = TestCityBuilder().build()
            val weatherDetails = TestWeatherDetailsBuilder().build()
            currentCityStorage.setCity(city)
            weatherDetailsRepository.put(city, weatherDetails)

            val observer = viewModel.observeState().test(this)

            advanceTimeBy(DELAY_MILLIS_CITY_LOAD)
            runCurrent()

            observer
                .assertValueCount(1)
                .assertLatestValue(WeatherDetailsState.Loading)

            advanceTimeBy(DELAY_MILLIS_API_LOAD)
            runCurrent()

            observer
                .assertValueCount(2)
                .assertLatestValue { it is WeatherDetailsState.Data }
                .finish()
        }

    companion object {
        private const val DELAY_MILLIS_CITY_LOAD = 10_000L
        private const val DELAY_MILLIS_API_LOAD = 20_000L
        private const val TEXT_CHANGE_CITY = "changeCity"
        private const val TEXT_CITY_MISSING_MESSAGE = "cityMissing"
        private const val TEXT_SELECT_CITY = "selectCity"
        private const val TEXT_WEATHER_CONDITIONS_IMAGE_CONTENT_DESCRIPTION =
            "weatherConditionsImageContentDescription"
        private const val TEXT_TEMPERATURE_DAY = "day: $DEFAULT_TEMPERATURE"
        private const val TEXT_TEMPERATURE_NIGHT = "night: $DEFAULT_TEMPERATURE"
        private const val TEXT_TEMPERATURE_FEELS_LIKE = "feelsLike: $DEFAULT_TEMPERATURE"
        private const val CELSIUS_DEGREES = "°C"
    }
}
