package com.local.books.di.molule

import android.app.Application
import androidx.room.Room
import com.local.books.App
import com.local.books.repository.Repository
import com.local.books.retrofit.GoogleService
import com.local.books.retrofit.RetrofitApi
import com.local.books.room.BookDao
import com.local.books.room.RoomBookDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainDataModule(private val application: Application) {

    lateinit var dataBase : RoomBookDatabase

    @Provides
    @Singleton
    fun provideRepository(serviceApi: GoogleService, bookDao: BookDao): Repository {
        return Repository(serviceApi, bookDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): GoogleService {
        return RetrofitApi().serviceApi
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application): RoomBookDatabase {
        dataBase = Room.databaseBuilder(
            context.applicationContext,
            RoomBookDatabase::class.java,
            "myDB.db"
        )
            .build()

        return dataBase
    }

    @Provides
    @Singleton
    fun provideBookDao(db : RoomBookDatabase) : BookDao{
        return dataBase.bookDao()
    }


    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

}