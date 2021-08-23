[![pipeline](https://github.com/krossovochkin/KWeather/actions/workflows/pipeline-android.yml/badge.svg)](https://github.com/krossovochkin/KWeather/actions/workflows/pipeline-android.yml)[![pipeline](https://github.com/krossovochkin/KWeather/actions/workflows/pipeline-desktop.yml/badge.svg)](https://github.com/krossovochkin/KWeather/actions/workflows/pipeline-desktop.yml)[![pipeline](https://github.com/krossovochkin/KWeather/actions/workflows/pipeline-web.yml/badge.svg)](https://github.com/krossovochkin/KWeather/actions/workflows/pipeline-web.yml)

# KWeather
Jetpack Compose Kotlin MPP Weather app

## Description

- Kotlin Multiplatform (Android, Desktop, Web)
- Jetpack Compose
- MVI-like app architecture

## Kotlin MPP Features

- Ktor
- Coroutines/Flow
- Kotlin serialization
- SqlDelight
- Kodein
- Localization
- Image Loading (Coil)
- Basic navigation

## Jetpack Compose Features

- Text
- Image
- Text Input
- Button
- List
- TopAppBar
- HorizontalPager

## App Features

### Weather details for given city

#### Android
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/weather_details_today.png" width="200"/><img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/weather_details_tomorrow.png" width="200"/><img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/weather_details_week.png" width="200"/>

#### Desktop
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/weather_details_desktop.png" width="400"/>

#### Web
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/weather_details_web.png" width="200"/>

### City selection by name

#### Android
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/city_list.png" width="200"/>

#### Desktop
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/city_list_desktop.png" width="400"/>

#### Web
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/city_list_web.png" width="400"/>

## Module structure

- core - application agnostic modules (navigation, i18n, etc.)
- app - application specific modules (domain, network API, etc.)
- feature - application features/screens
- shared - facade of all modules that are required for application
- target - implementations of the apps for particular platforms (android, desktop, etc.)
