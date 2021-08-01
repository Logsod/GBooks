package com.local.books.viewmodel

import androidx.lifecycle.ViewModel
import com.local.books.model.Book
import com.local.books.repository.Repository
import com.local.books.viewmodel.mapper.BookMapper
import io.reactivex.Observable
import javax.inject.Inject

class BookActivityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    fun deleteBook(id: String) = repository.bookDao.deleteBook(id)
    fun selectAllBook(): Observable<List<Book>> =
        repository.bookDao.selectAllBook().map { BookMapper().fromEnityList(it) }.toObservable()

}