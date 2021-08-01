package com.local.books

import android.app.Application
import com.local.books.di.component.AppComponent
import com.local.books.di.component.DaggerAppComponent
import com.local.books.di.molule.MainDataModule

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().mainDataModule(MainDataModule(this)).build()
    }
}