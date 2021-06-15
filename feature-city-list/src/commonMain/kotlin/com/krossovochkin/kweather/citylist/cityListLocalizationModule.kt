package com.krossovochkin.kweather.citylist

import com.krossovochkin.kweather.citylist.data.CityListRepositoryImpl
import com.krossovochkin.kweather.citylist.domain.CityListRepository
import com.krossovochkin.kweather.citylist.domain.GetCityListInteractor
import com.krossovochkin.kweather.citylist.domain.GetCityListInteractorImpl
import com.krossovochkin.kweather.citylist.domain.SelectCityInteractor
import com.krossovochkin.kweather.citylist.domain.SelectCityInteractorImpl
import com.krossovochkin.kweather.citylist.presentation.CityListViewModel
import com.krossovochkin.kweather.citylist.presentation.CityListViewModelImpl
import com.krossovochkin.kweather.core.storage.citylist.DbCityListDatasource
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
