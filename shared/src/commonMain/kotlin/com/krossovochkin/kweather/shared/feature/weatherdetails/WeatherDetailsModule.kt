package com.krossovochkin.kweather.shared.feature.weatherdetails

import com.krossovochkin.kweather.shared.common.image.ImageLoader
import com.krossovochkin.kweather.shared.common.localization.LocalizationManager
import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.GetCurrentCityInteractorImpl
import com.krossovochkin.kweather.shared.common.storage.CurrentCityStorage
import com.krossovochkin.kweather.shared.common.storage.CurrentCityStorageImpl
import com.krossovochkin.kweather.shared.common.storage.StorageModule
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapper
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapperImpl
import com.krossovochkin.kweather.shared.feature.weatherdetails.data.*
import com.krossovochkin.kweather.shared.feature.weatherdetails.data.WeatherDetailsApiClient
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.GetWeatherDetailsInteractorImpl
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.WeatherDetailsRepository
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsViewModelImpl
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

class WeatherDetailsModule(
    private val storageModule: StorageModule,
    private val router: Router,
    private val imageLoader: ImageLoader,
    private val localizationManager: LocalizationManager,
    private val apiKey: String
) {

    val viewModel: WeatherDetailsViewModel
        get() = WeatherDetailsViewModelImpl(
            router = router,
            getWeatherDetailsInteractor = getWeatherDetailsInteractor,
            getCurrentCityInteractor = getCurrentCityInteractor,
            imageLoader = imageLoader,
            localizationManager = localizationManager
        )


    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor
        get() = GetWeatherDetailsInteractorImpl(
            weatherDetailsRepository = weatherDetailsRepository
        )

    private val getCurrentCityInteractor: GetCurrentCityInteractor
        get() = GetCurrentCityInteractorImpl(
            currentCityStorage = currentCityStorage
        )

    private val currentCityStorage: CurrentCityStorage
        get() = CurrentCityStorageImpl(
            storage = storageModule.storage,
            cityListMapper = cityListMapper
        )

    private val weatherDetailsRepository: WeatherDetailsRepository
        get() = WeatherDetailsRepositoryImpl(
            weatherDetailsApi = weatherDetailsApi,
            weatherDetailsMapper = weatherDetailsMapper
        )

    private val weatherDetailsApi: WeatherDetailsApi
        get() = WeatherDetailsApiClient(
            client = httpClient,
            apiKey = apiKey
        )

    private val weatherDetailsMapper: WeatherDetailsMapper
        get() = WeatherDetailsMapperImpl()

    private val httpClient: HttpClient
        get() = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    json = Json.nonstrict
                )
            }
        }

    private val cityListMapper: CityListMapper
        get() = CityListMapperImpl()
}