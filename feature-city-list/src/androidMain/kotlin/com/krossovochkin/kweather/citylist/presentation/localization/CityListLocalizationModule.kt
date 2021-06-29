package com.krossovochkin.kweather.citylist.presentation.localization

import com.krossovochkin.i18n.LocalizationManager
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

actual val cityListLocalizationModule = DI.Module("CityListLocalizationModule") {

    bind<LocalizationManager<LocalizedStringKey>>() with singleton {
        LocalizationManagerImpl(
            context = instance()
        )
    }
}
