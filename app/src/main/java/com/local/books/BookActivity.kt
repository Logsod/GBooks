package com.local.books

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.local.books.databinding.ActivityBookBinding
import com.local.books.di.viewmodel.ViewModelFactory
import com.local.books.model.Book
import com.local.books.recyclerviewitems.BookAdapterItem
import com.local.books.recyclerviewitems.GridSpacingItemDecoration
import com.local.books.viewmodel.BookActivityViewModel
import com.xwray.groupie.GroupieAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BookActivity : AppCompatActivity(), BookAdapterItem.OnHeartClickedListener {

    lateinit var binding: ActivityBookBinding

    //@Inject
    //lateinit var bookDao: BookDao
    lateinit var adapter: GroupieAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val bookActivityViewModel: BookActivityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(BookActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        (application as App).appComponent.inject(this)

        adapter = GroupieAdapter()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_book)
        binding.recyclerViewBookActivity.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewBookActivity.adapter = adapter

        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);


        binding.recyclerViewBookActivity.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                20,
                10
            )
        )
        val d = bookActivityViewModel.selectAllBook().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ bookList ->
                bookList.forEach {
                    adapter.add(BookAdapterItem(it, this))
                }
            }, {})


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }

    override fun onHeartClicked(item: BookAdapterItem, book: Book) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setMessage("Are you sure you want to Delete?")
            .setPositiveButton("Yes") { dialog, id ->
                bookActivityViewModel.deleteBook(item.book.id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe()
                adapter.remove(item)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}