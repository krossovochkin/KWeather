package com.krossovochkin.kweather.citylist.presentation

import com.krossovochkin.i18n.LocalizationManager
import com.krossovochkin.kweather.citylist.domain.GetCityListInteractor
import com.krossovochkin.kweather.citylist.domain.SelectCityInteractor
import com.krossovochkin.kweather.citylist.presentation.localization.LocalizedStringKey
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.domain.CityLocation
import com.krossovochkin.kweather.navigation.RouterDestination
import com.krossovochkin.location.LocationProvider
import com.krossovochkin.navigation.Router
import com.krossovochkin.presentation.BaseViewModel
import com.krossovochkin.presentation.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface CityListViewModel : ViewModel<CityListState, CityListAction>

class CityListViewModelImpl(
    private val getCityListInteractor: GetCityListInteractor,
    private val selectCityInteractor: SelectCityInteractor,
    private val router: Router<RouterDestination>,
    private val localizationManager: LocalizationManager<LocalizedStringKey>,
    private val locationProvider: LocationProvider
) : BaseViewModel<CityListState, CityListAction, CityListActionResult>(CityListState.Loading),
    CityListViewModel {

    private val queryChanges = MutableStateFlow("")

    init {
        queryChanges
            .debounce(timeoutMillis = 300L)
            .onEach {
                performAction(CityListAction.Load)
            }
            .launchIn(scope)

        performAction(CityListAction.Load)
    }

    override suspend fun reduceState(
        state: CityListState,
        result: CityListActionResult
    ): CityListState = when (state) {
        CityListState.Loading -> {
            when (result) {
                is CityListActionResult.Loaded -> CityListState.Data(
                    queryText = "",
                    cityList = result.cityList,
                    cityNameHintText = localizationManager.getString(LocalizedStringKey.CityList_CityNameHint),
                    useCurrentLocationText = localizationManager
                        .getString(LocalizedStringKey.CityList_UseCurrentLocation)
                )
                is CityListActionResult.CityNameQueryChanged -> reducerError(
                    state,
                    result
                )
            }
        }
        is CityListState.Data -> {
            when (result) {
                is CityListActionResult.Loaded -> state.copy(
                    cityList = result.cityList
                )
                is CityListActionResult.CityNameQueryChanged -> state.copy(
                    queryText = result.query
                )
            }
        }
        is CityListState.Error -> {
            when (result) {
                is CityListActionResult.Loaded -> reducerError(state, result)
                is CityListActionResult.CityNameQueryChanged -> reducerError(
                    state,
                    result
                )
            }
        }
    }

    override fun performAction(action: CityListAction) {
        when (action) {
            CityListAction.Load -> {
                scope.launch {
                    val query = (state as? CityListState.Data)?.queryText.orEmpty()
                    onActionResult(CityListActionResult.Loaded(getCityListInteractor.get(query)))
                }
            }
            is CityListAction.SelectCity -> {
                scope.launch {
                    selectCityInteractor.select(action.city)
                    router.navigateTo(RouterDestination.WeatherDetails)
                }
            }
            is CityListAction.ChangeCityNameQuery -> {
                scope.launch {
                    queryChanges.value = action.query
                    onActionResult(CityListActionResult.CityNameQueryChanged(action.query))
                }
            }
            CityListAction.UseCurrentLocation -> {
                scope.launch {
                    selectCityInteractor.select(
                        City(
                            id = CityId.currentLocation,
                            name = localizationManager.getString(LocalizedStringKey.CityList_UseCurrentLocation),
                            location = locationProvider.getLastLocation()
                                .let { location ->
                                    CityLocation(
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    )
                                }
                        )
                    )
                    router.navigateTo(RouterDestination.WeatherDetails)
                }
            }
            CityListAction.Back -> {
                scope.launch { router.navigateBack() }
            }
        }
    }
}
