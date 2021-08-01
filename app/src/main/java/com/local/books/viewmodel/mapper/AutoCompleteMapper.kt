package com.local.books.viewmodel.mapper

import com.local.books.retrofit.GoogleBookAutoComplete
import com.local.books.retrofit.GoogleBookResponse

class AutoCompleteMapper() {

    fun fromResponse(googleBookResponse: GoogleBookResponse): GoogleBookAutoComplete {
        return GoogleBookAutoComplete(
            googleBookResponse.items.map { it.volumeInfo.title }
        )
    }
}