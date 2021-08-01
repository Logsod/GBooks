package com.local.books.room

import androidx.room.*
import com.local.books.model.Book
import io.reactivex.Single

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: BookEntity): Single<Long>

    @Query(value = "DELETE FROM BOOK WHERE 1")
    fun deleteAllBook(): Single<Int>

    @Query("DELETE FROM BOOK WHERE id Like :id")
    fun deleteBook(id: String): Single<Int>

    @Query("SELECT * FROM BOOK WHERE id Like :id")
    fun selectBook(id: String): Single<BookEntity>

    @Query("SELECT * FROM Book")
    fun selectAllBook(): Single<List<BookEntity>>
}