package com.krossovochkin.kweather.shared.feature.setup

import com.krossovochkin.kweather.shared.common.localization.LocalizationManager
import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.common.storage.StorageModule
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapperImpl
import com.krossovochkin.kweather.shared.feature.setup.data.SetupRepositoryImpl
import com.krossovochkin.kweather.shared.feature.setup.domain.SetupInteractor
import com.krossovochkin.kweather.shared.feature.setup.domain.SetupInteractorImpl
import com.krossovochkin.kweather.shared.feature.setup.domain.SetupRepository
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupViewModel
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupViewModelImpl

class SetupModule(
    private val router: Router,
    private val localizationManager: LocalizationManager,
    private val storageModule: StorageModule
) {

    val viewModel: SetupViewModel
        get() = SetupViewModelImpl(
            router = router,
            localizationManager = localizationManager,
            setupInteractor = setupInteractor
        )

    private val setupInteractor: SetupInteractor
        get() = SetupInteractorImpl(
            setupRepository = setupRepository
        )

    private val setupRepository: SetupRepository
        get() = SetupRepositoryImpl(
            inputCityListDatasource = storageModule.fileCityListDatasource,
            outputCityListDatasource = storageModule.dbCityListDatasource
        )
}