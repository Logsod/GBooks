package com.local.books.di.viewmodel

import androidx.lifecycle.ViewModel
import com.local.books.viewmodel.BookActivityViewModel
import com.local.books.viewmodel.MainActivityViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindSearchBookActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookActivityViewModel::class)
    internal abstract fun bindBookActivityViewModel(bookActivityViewModel: BookActivityViewModel): ViewModel


}