package com.example.myhomeapp.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppProviderModule::class])
interface AppComponent {

    //fun inject(fragment: NewsFeedFragment)
    //fun inject(fragment: ConverterFragment)
    //fun inject(fragment: NewsSearchFragment)
}