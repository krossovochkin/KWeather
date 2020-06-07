package com.krossovochkin.kweather.shared.feature.setup

import com.krossovochkin.kweather.shared.common.storage.citylist.DbCityListDatasource
import com.krossovochkin.kweather.shared.common.storage.citylist.FileCityListDatasource
import com.krossovochkin.kweather.shared.feature.setup.data.SetupRepositoryImpl
import com.krossovochkin.kweather.shared.feature.setup.domain.SetupInteractor
import com.krossovochkin.kweather.shared.feature.setup.domain.SetupInteractorImpl
import com.krossovochkin.kweather.shared.feature.setup.domain.SetupRepository
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupViewModel
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupViewModelImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val setupModule = DI.Module("SetupModule") {

    bind<SetupViewModel>() with singleton {
        SetupViewModelImpl(
            router = instance(),
            setupInteractor = instance()
        )
    }

    bind<SetupInteractor>() with singleton {
        SetupInteractorImpl(
            setupRepository = instance()
        )
    }

    bind<SetupRepository>() with singleton {
        SetupRepositoryImpl(
            inputCityListDatasource = instance(tag = FileCityListDatasource::class),
            outputCityListDatasource = instance(tag = DbCityListDatasource::class)
        )
    }
}
