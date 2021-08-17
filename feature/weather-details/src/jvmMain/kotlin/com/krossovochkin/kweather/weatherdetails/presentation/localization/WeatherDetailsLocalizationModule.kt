package com.krossovochkin.kweather.weatherdetails.presentation.localization

import com.krossovochkin.i18n.LocalizationManager
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

internal actual val weatherDetailsLocalizationModule = DI.Module("WeatherDetailsLocalizationModule") {
    bind<LocalizationManager<LocalizedStringKey>>() with singleton {
        LocalizationManagerImpl()
    }
}
