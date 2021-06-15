package com.krossovochkin.kweather.setup

import com.krossovochkin.kweather.setup.data.DbCityListInitializer
import com.krossovochkin.kweather.setup.data.FileCityListProvider
import com.krossovochkin.kweather.setup.data.SetupRepositoryImpl
import com.krossovochkin.kweather.setup.domain.SetupInteractor
import com.krossovochkin.kweather.setup.domain.SetupInteractorImpl
import com.krossovochkin.kweather.setup.domain.SetupRepository
import com.krossovochkin.kweather.setup.presentation.SetupViewModel
import com.krossovochkin.kweather.setup.presentation.SetupViewModelImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

actual val setupModule = DI.Module("SetupModule") {

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

    bind<FileCityListProvider>() with singleton {
        FileCityListProvider(
            context = instance()
        )
    }

    bind<DbCityListInitializer>() with singleton {
        DbCityListInitializer(
            dao = instance()
        )
    }

    bind<SetupRepository>() with singleton {
        SetupRepositoryImpl(
            cityListProvider = instance(),
            cityListInitializer = instance()
        )
    }
}