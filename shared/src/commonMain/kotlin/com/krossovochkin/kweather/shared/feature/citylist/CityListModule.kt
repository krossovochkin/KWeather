package com.krossovochkin.kweather.shared.feature.citylist

import com.krossovochkin.kweather.shared.common.storage.CurrentCityStorage
import com.krossovochkin.kweather.shared.common.storage.CurrentCityStorageImpl
import com.krossovochkin.kweather.shared.common.storage.citylist.DbCityListDatasource
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapper
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapperImpl
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListRepositoryImpl
import com.krossovochkin.kweather.shared.feature.citylist.domain.*
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListViewModel
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListViewModelImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val cityListModule = DI.Module("CityListModule") {

    bind<CityListViewModel>() with singleton {
        CityListViewModelImpl(
            router = instance(),
            getCityListInteractor = instance(),
            selectCityInteractor = instance(),
            localizationManager = instance()
        )
    }

    bind<GetCityListInteractor>() with singleton {
        GetCityListInteractorImpl(
            cityListRepository = instance()
        )
    }

    bind<SelectCityInteractor>() with singleton {
        SelectCityInteractorImpl(
            currentCityStorage = instance()
        )
    }

    bind<CityListRepository>() with singleton {
        CityListRepositoryImpl(
            cityListDatasource = instance(tag = DbCityListDatasource::class),
            cityListMapper = instance()
        )
    }
}
