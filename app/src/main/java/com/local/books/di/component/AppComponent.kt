package com.local.books.di.component

import com.local.books.BookActivity
import com.local.books.SearchBookActivity
import com.local.books.di.molule.MainDataModule
import com.local.books.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainDataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: SearchBookActivity)
    fun inject(activity: BookActivity)
}