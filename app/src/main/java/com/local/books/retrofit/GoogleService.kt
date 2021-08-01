package com.local.books.retrofit

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GoogleService {
    @GET("volumes")
    fun searchBook(@Query("q") q: String) : Single<GoogleBookResponse>

    @GET("volumes")
    suspend fun autoComplete(@Query("q") q: String) : Response<GoogleBookResponse>
}