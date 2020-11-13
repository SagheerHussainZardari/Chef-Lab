package com.sagheerhussainzardari.cheflab.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sagheerhussainzardari.cheflab.Fragments.HomeFragment
import com.sagheerhussainzardari.cheflab.Models.BookSellModel
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.recycler_layout_bookforsell.view.*

class BookSellAdapter(
    var context: Context,
    var booksList: ArrayList<BookSellModel>,
    var homeFragment: HomeFragment
) :
    RecyclerView.Adapter<BookSellAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_layout_bookforsell, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.view.bookItemCard.setOnClickListener {
//            homeFragment.onBookCardClicked(booksList[position].bookId.toInt())
        }
        if (booksList[position].bookRatings == "0.0") {
            holder.view.tv_BookRatings.visibility = View.GONE
        }
        holder.view.tv_BookName.text = booksList[position].bookName
        holder.view.tv_BookPrice.text = "Rs." + booksList[position].bookPrice
        holder.view.tv_BookRatings.text = booksList[position].bookRatings
        Glide.with(context).load(booksList[position].bookImageUrl).into(holder.view.iv_BookImage)
    }
}