package com.local.books.model

data class SearchResult(
    val items: List<Item>
) {
    data class Item(
        val id : String,
        val title: String,
        val author: String,
        val smallImage: String,
        val image: String,
        val description: String
    )
}
