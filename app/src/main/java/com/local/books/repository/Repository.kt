package com.local.books.repository

import com.local.books.retrofit.GoogleService
import com.local.books.retrofit.RetrofitApi
import com.local.books.room.BookDao
import com.local.books.room.RoomBookDatabase
import javax.inject.Inject

class Repository @Inject constructor(

    var googleService: GoogleService,
    var bookDao: BookDao
) {

}