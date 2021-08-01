package com.local.books.viewmodel

import androidx.lifecycle.*
import com.local.books.model.Book
import com.local.books.model.SearchResult
import com.local.books.repository.Repository
import com.local.books.retrofit.GoogleBookAutoComplete
import com.local.books.room.BookDao
import com.local.books.viewmodel.mapper.AutoCompleteMapper
import com.local.books.viewmodel.mapper.BookMapper
import com.local.books.viewmodel.mapper.SearchResultMapper
import io.reactivex.Observable
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val film = MutableLiveData("")
    val autoComplete = MutableLiveData<List<String>>(listOf())

    fun searchBook(q: String): Observable<SearchResult> {

        return repository.googleService.searchBook(q)
            .map {
                SearchResultMapper().fromResponse(it)
            }
            .toObservable()
    }

    //this google api not provide autocomplete, ugly response mapping for testing
    fun getAutoCompleteString(q: String): Observable<GoogleBookAutoComplete> {
        return repository.googleService.searchBook(q)
            .map { AutoCompleteMapper().fromResponse(it) }
            .toObservable()
    }

    fun selectAllBook(): Observable<List<Book>> =
        repository.bookDao.selectAllBook().map { BookMapper().fromEnityList(it) }.toObservable()

    fun insertBook(book: Book) = repository.bookDao.insertBook(BookMapper().toEntity(book))
    fun deleteBook(id: String) = repository.bookDao.deleteBook(id)
}