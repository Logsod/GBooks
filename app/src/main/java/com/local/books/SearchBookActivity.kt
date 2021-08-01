package com.local.books

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.local.books.adapter.SearchAutoCompleteAdapter
import com.local.books.databinding.ActivitySearchBookBinding
import com.local.books.di.viewmodel.ViewModelFactory
import com.local.books.model.Book
import com.local.books.model.ImageBinding
import com.local.books.recyclerviewitems.BookAdapterItem
import com.local.books.recyclerviewitems.GridSpacingItemDecoration
import com.local.books.room.BookDao
import com.local.books.room.RoomBookDatabase
import com.local.books.utils.log
import com.local.books.viewmodel.mapper.BookMapper
import com.local.books.viewmodel.MainActivityViewModel
import com.xwray.groupie.GroupieAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchBookActivity @Inject constructor() : AppCompatActivity(),
    BookAdapterItem.OnHeartClickedListener {

    companion object {
        const val TAG_BOOK = "TAG_BOOK"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mainActivityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }
    private val disposable = CompositeDisposable()
    val adapter = GroupieAdapter()
    private val bookMap = ArrayList<String>()
    lateinit var binding: ActivitySearchBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_book)
        binding()
        subscribe()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun subscribe() {
        val d = mainActivityViewModel.selectAllBook().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ booksEntity ->
                booksEntity.forEach { bookEntity ->
                    bookMap.add(bookEntity.id)
                }
                startSearch("lord of")


            }, {
                it.message?.log(this.toString())
            })
        disposable.add(d)
    }

    private fun startSearch(query: String) {
        val d = mainActivityViewModel.searchBook(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { BookMapper().fromSearchResultItem(it) }
            .subscribe({ books ->
                adapter.clear()
                books.forEach { book ->
                    book.bookmark = bookMap.contains(book.id)
                    adapter.add(BookAdapterItem(book, this))
                }
            }, {
            })
    }


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    private fun binding() {


        binding.startSearchSearchActivity.setOnClickListener {
            startSearch(binding.autoCompleteTextSearchActivity.text.toString().trim())
        }

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.itemCount % 2 == 1 && position == adapter.itemCount - 1) 2 else 1
            }
        }
        binding.recyclerViewSearchbookSearchActivity.layoutManager = layoutManager
        binding.recyclerViewSearchbookSearchActivity.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                20,
                10
            )
        )

        binding.recyclerViewSearchbookSearchActivity.adapter = adapter

        //val mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = mainActivityViewModel

        val searchAutoCompleteAdapter =
            SearchAutoCompleteAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                mutableListOf()
            )
        binding.autoCompleteTextSearchActivity.setAdapter(searchAutoCompleteAdapter)


        binding.autoCompleteTextSearchActivity.doAfterTextChanged {

            if (binding.autoCompleteTextSearchActivity.text.length < 2) return@doAfterTextChanged

            mainActivityViewModel.getAutoCompleteString(binding.autoCompleteTextSearchActivity.text.toString())
                //.skip(1)
                .debounce(400, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    searchAutoCompleteAdapter.clear()
                    searchAutoCompleteAdapter.data.clear()
                    it.items.forEach {
                        searchAutoCompleteAdapter.add(it)
                    }
                    searchAutoCompleteAdapter.filter.filter(
                        binding.autoCompleteTextSearchActivity.getText(),
                        null
                    );
                }, {})
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.id_menu_library)
        {
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onHeartClicked(item: BookAdapterItem, book: Book) {
        book.bookmark = !book.bookmark
        book.bookmark.toString().log(this.toString())
        if (book.bookmark) {
            item.binding.imageBinding = ImageBinding(R.drawable.ic_heart_431)
            mainActivityViewModel.insertBook(book).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe()
        } else {
            item.binding.imageBinding = ImageBinding(R.drawable.ic_heart_3510)
            mainActivityViewModel.deleteBook(book.id).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe()
        }
    }
}