package com.krossovochkin.kweather.shared.feature.citylist

import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.common.storage.CurrentCityStorage
import com.krossovochkin.kweather.shared.common.storage.CurrentCityStorageImpl
import com.krossovochkin.kweather.shared.common.storage.MutableCurrentCityStorage
import com.krossovochkin.kweather.shared.common.storage.StorageModule
import com.krossovochkin.kweather.shared.feature.citylist.data.*
import com.krossovochkin.kweather.shared.feature.citylist.domain.*
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListViewModel
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListViewModelImpl
import kotlinx.coroutines.CoroutineScope

class CityListModule(
    private val router: Router,
    private val storageModule: StorageModule
) {

    val viewModel: CityListViewModel
        get() = CityListViewModelImpl(
            router = router,
            getCityListInteractor = getCityListInteractor,
            selectCityInteractor = selectCityInteractor
        )

    private val getCityListInteractor: GetCityListInteractor
        get() = GetCityListInteractorImpl(
            cityListRepository = cityListRepository
        )

    private val selectCityInteractor: SelectCityInteractor
        get() = SelectCityInteractorImpl(
            currentCityStorage = currentCityStorage
        )

    private val currentCityStorage: MutableCurrentCityStorage
        get() = CurrentCityStorageImpl(
            storage = storageModule.storage,
            cityListMapper = cityListMapper
        )

    private val cityListRepository: CityListRepository
        get() = CityListRepositoryImpl(
            cityListStorage = storageModule.cityListStorage,
            cityListMapper = cityListMapper
        )

    private val cityListMapper: CityListMapper by lazy { CityListMapperImpl() }
}