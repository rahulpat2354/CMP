package com.cis.cmp.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.dsl.module

actual val platformModule = module {
    single<Settings> {
        val androidContext = (getPlatformContext() as AndroidPlatformContext).context
        val sharedPreferences = androidContext.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        SharedPreferencesSettings(sharedPreferences)
    }
}