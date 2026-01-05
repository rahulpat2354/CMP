package com.cis.cmp.di

import com.cis.cmp.data.api.ApiService
import com.cis.cmp.data.network.HttpClientFactory
import com.cis.cmp.data.preference.PreferenceManager
import com.cis.cmp.data.preference.PreferenceManagerImpl
import com.cis.cmp.data.repository.AuthRepository
import com.cis.cmp.data.repository.AuthRepositoryImplementation
import com.cis.cmp.data.repository.MainRepository
import com.cis.cmp.data.repository.MainRepositoryImpl
import com.cis.cmp.ui.authentication.LoginViewModel
import com.cis.cmp.ui.home.HomeViewModel
import com.cis.cmp.ui.splash.SplashViewModel
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val preferenceModule = module {
    single<PreferenceManager> { PreferenceManagerImpl(get()) }
}

val networkModule = module {
    single { HttpClientFactory.create(get()) }

    single { ApiService(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImplementation(get(), get()) }
    single<MainRepository> { MainRepositoryImpl(get(), get()) }
}

val viewmodelModule = module {
    factory {
        SplashViewModel(get())
    }
    factory {
        LoginViewModel(get())
    }
    factory {
        HomeViewModel(get(), get())
    }
}

val appModules = listOf(networkModule, preferenceModule, repositoryModule, viewmodelModule, platformModule)