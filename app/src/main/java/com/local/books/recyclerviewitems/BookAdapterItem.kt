package com.local.books.recyclerviewitems

import com.local.books.R
import com.local.books.databinding.RowRecycleivewBookBinding
import com.local.books.model.Book
import com.local.books.model.ImageBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.databinding.BindableItem

class BookAdapterItem(val book: Book, val heartLisneter: OnHeartClickedListener) :
    BindableItem<RowRecycleivewBookBinding>() {
    lateinit var binding: RowRecycleivewBookBinding

    interface OnHeartClickedListener {
        fun onHeartClicked(item: BookAdapterItem, book: Book)
    }

    override fun bind(viewBinding: RowRecycleivewBookBinding, position: Int) {
        binding = viewBinding
        viewBinding.book = book
        if(book.bookmark)
            viewBinding.imageBinding = ImageBinding(R.drawable.ic_heart_431)
        else
            viewBinding.imageBinding = ImageBinding(R.drawable.ic_heart_3510)

        viewBinding.imageViewAddbookRow.setOnClickListener {
            heartLisneter.onHeartClicked(this@BookAdapterItem, book)
        }
        if (book.image.isNotEmpty())
            Picasso.get()
                .load(book.image)
                .into(viewBinding.imageViewBookImage)
        //viewBinding.textViewBookTitle.text = book.title
    }

    override fun getLayout(): Int {
        return R.layout.row_recycleivew_book
    }

}