package com.example.myhomeapp.di

import com.example.myhomeapp.ui.fragments.eventHistory.EventHistoryFragment
import com.example.myhomeapp.ui.fragments.recentEvents.RecentEventsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppProviderModule::class])
interface AppComponent {

    fun inject(fragment: RecentEventsFragment)
    fun inject(fragment: EventHistoryFragment)
}