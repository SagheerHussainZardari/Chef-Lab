package com.sagheerhussainzardari.cheflab.Adapters

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheerhussainzardari.cheflab.Fragments.MyBooksForSellFragment
import com.sagheerhussainzardari.cheflab.Models.IngredentsModel
import com.sagheerhussainzardari.cheflab.R

class BookSellMyBooksForSellAdapter(
    var context: Context,
    var booksList: ArrayList<IngredentsModel>,
    var booksForSellFragment: MyBooksForSellFragment
) :
    RecyclerView.Adapter<BookSellMyBooksForSellAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_layout_mybooksforsell, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//
//        holder.view.btn_DeleteMyBooksForSell.setOnClickListener {
//            if (isInternetAvaiable())
//                booksForSellFragment.onBookDelete(booksList[position])
//            else
//                context.toastshort("No Internet Connection...\nTry Again...")
//        }
//        holder.view.btn_EditMyBooksForSell.setOnClickListener {
//            if (isInternetAvaiable())
//                booksForSellFragment.onBookEdit(booksList[position])
//            else
//                context.toastshort("No Internet Connection...\nTry Again...")
//        }
//
//        Glide.with(context).load(booksList[position].bookImageUrl)
//            .into(holder.view.iv_BookImageMyBooksForSell)
//        holder.view.tv_BookNameMyBooksForSell.text = booksList[position].bookName
    }


    private fun isInternetAvaiable(): Boolean {
        val c = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return c.activeNetworkInfo != null && c.activeNetworkInfo.isConnected
    }

}