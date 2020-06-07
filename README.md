# KWeather
Jetpack Compose Kotlin MPP Weather app

## Description

- Kotlin Multiplatform (common and Android-only)
- Jetpack Compose (for Android app UI)
- MVI-like app architecture

## Kotlin MPP Features

- Ktor
- Coroutines/Flow
- Kotlin serialization
- SqlDelight
- **TBD** Kodein
- Localization
- Image Loading (Coil)
- Basic navigation

## Jetpack Compose Features

- Text
- Image
- Text Input
- List

## App Features

- Setup (populate local database for better performance) on first app start
- Weather details (temperature and weather conditions) for given city
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/weather_details.png" width="200"/>
- City selection by name (with text input debounce)
<img src="https://raw.githubusercontent.com/krossovochkin/KWeather/master/doc/city_list.png" width="200"/>
