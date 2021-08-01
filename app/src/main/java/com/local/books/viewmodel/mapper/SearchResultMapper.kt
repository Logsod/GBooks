package com.local.books.viewmodel.mapper

import com.local.books.model.SearchResult
import com.local.books.retrofit.GoogleBookResponse

class SearchResultMapper() {
    private fun itemFromResponse(googleBookResponseItem: GoogleBookResponse.Item): SearchResult.Item {
        return SearchResult.Item(
            id = googleBookResponseItem.id,
            title = googleBookResponseItem.volumeInfo.title,
            author = googleBookResponseItem.volumeInfo.authors.firstOrNull() ?: "",
            smallImage = googleBookResponseItem.volumeInfo.imageLinks.smallThumbnail,
            image = googleBookResponseItem.volumeInfo.imageLinks.thumbnail,
            description = googleBookResponseItem.volumeInfo.description
        )
    }

    fun fromResponse(googleBookResponse: GoogleBookResponse): SearchResult {
        return SearchResult(
            items = googleBookResponse.items.map { itemFromResponse(it) }
        )
    }
}