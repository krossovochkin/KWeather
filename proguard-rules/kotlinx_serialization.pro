-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.krossovochkin.kweather.**$$serializer { *; }
-keepclassmembers class com.krossovochkin.kweather.** {
    *** Companion;
}
-keepclasseswithmembers class com.krossovochkin.kweather.** {
    kotlinx.serialization.KSerializer serializer(...);
}
