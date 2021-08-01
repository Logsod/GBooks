package com.local.books.viewmodel.mapper

import com.local.books.model.Book
import com.local.books.model.SearchResult
import com.local.books.room.BookEntity

class BookMapper {
    fun fromSearchResultItem(searchResult: SearchResult): List<Book> {
        return searchResult.items.map {
            Book(
                id = it.id,
                title = it.title,
                description = it.description,
                image = it.image,
                bookmark = false
            )
        }
    }

    fun toEntity(book: Book): BookEntity {
        return BookEntity(
            id = book.id,
            title = book.title,
            description = book.description,
            image = book.image
        )
    }

    fun fromEntity(entity: BookEntity): Book {
        return Book(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            image = entity.image,
            bookmark = true
        )
    }

    fun fromEnityList(entity: List<BookEntity>) : List<Book>
    {
        return entity.map { fromEntity(it) }
    }
}