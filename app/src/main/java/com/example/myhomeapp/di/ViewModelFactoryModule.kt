package com.example.myhomeapp.di

//import com.example.myhomeapp.ui.fragments.converter.ConverterViewModel
//import com.example.myhomeapp.ui.fragments.feed.NewsFeedViewModel
//import com.example.myhomeapp.ui.fragments.search.NewsSearchViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myhomeapp.ui.fragments.eventHistory.EventHistoryViewModel
import com.example.myhomeapp.ui.fragments.recentEvents.RecentEventsViewModel
import com.example.myhomeapp.utils.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bind(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    //@ViewModelKey(RecentEventsViewModel::class)
    abstract fun bindRecentEventsViewModel(viewModel: RecentEventsViewModel): ViewModel


    @Binds
    @IntoMap
    //@ViewModelKey(EventHistoryViewModel::class)
    abstract fun bindEventHistoryViewModel(viewModel: EventHistoryViewModel): ViewModel

    //@Binds
    //@IntoMap
    //@ViewModelKey(ConverterViewModel::class)
    //abstract fun bindConverterViewModel(viewModel: ConverterViewModel): ViewModel
}