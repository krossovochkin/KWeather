package com.krossovochkin.kweather.weathermap.presentation.localization

import com.krossovochkin.i18n.LocalizationManager
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

internal actual val weatherMapLocalizationModule = DI.Module("WeatherMapLocalizationModule") {
    bind<LocalizationManager<LocalizedStringKey>>() with singleton {
        LocalizationManagerImpl()
    }
}
