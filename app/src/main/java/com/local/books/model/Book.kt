package com.local.books.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(val id: String, val title:String, val description: String, val image:String, var bookmark : Boolean) : Parcelable {
    constructor() : this("","","","", false)
}