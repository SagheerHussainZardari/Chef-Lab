package com.sagheerhussainzardari.cheflab.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.cheflab.Adapters.BookSellAdapter
import com.sagheerhussainzardari.cheflab.MainActivity
import com.sagheerhussainzardari.cheflab.Models.BookSellModel
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        var booksList = ArrayList<BookSellModel>()
        var db = FirebaseDatabase.getInstance().getReference()

        var categoriesList = ArrayList<String>()
        var booksListFiltered = ArrayList<BookSellModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setUsersDetails()

        setCategoriesSpinner()

//        autoComplete_Search.addTextChangedListener {
//            if (it.toString() == "") {
//                if (spinner_ByCities.selectedItem.toString() == " All Categories") {
//                    setUpRecyclerView(booksList)
//                } else {
//                    setUpRecyclerView(booksListFiltered)
//                }
//            } else {
//                val newListBySearchValue = ArrayList<BookSellModel>()
//                if (spinner_ByCities.selectedItem.toString() == " All Categories") {
//                    for (book in booksList) {
//                        if (book.bookName.toLowerCase().contains(it.toString().toLowerCase()))
//                            newListBySearchValue.add(book)
//                    }
//                    setUpRecyclerView(newListBySearchValue)
//
//                } else {
//                    for (book in booksListFiltered) {
//                        if (book.bookName.toLowerCase().contains(it.toString().toLowerCase()))
//                            newListBySearchValue.add(book)
//                    }
//                    setUpRecyclerView(newListBySearchValue)
//                }
//
//            }
//        }

        progressBarLayoutHomeFragment.setOnClickListener { }

//        if (booksList.isEmpty()) {
//            getBooksList()
//        } else {
//            setCiteisSpinner()
//            setUpRecyclerView(booksList)
//        }
    }

//    fun getBooksList() {
//        progressBarLayoutHomeFragment.visibility = View.VISIBLE
//        db.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {}
//
//            override fun onDataChange(p0: DataSnapshot) {
//
//                for (document in p0.children) {
//                    booksList.add(
//                        BookSellModel(
//                            document.child("bookID").value.toString(),
//                            document.child("bookName").value.toString(),
//                            document.child("bookAuthor").value.toString(),
//                            document.child("bookPrice").value.toString(),
//                            document.child("bookSellerUID").value.toString(),
//                            document.child("bookSellerName").value.toString(),
//                            document.child("bookSellerPhone").value.toString(),
//                            document.child("bookSellerCity").value.toString(),
//                            document.child("bookRatings").value.toString(),
//                            document.child("bookImageUrl").value.toString()
//                        )
//                    )
//                }
//                setCiteisSpinner()
//                setUpRecyclerView(booksList)
//            }
//        })
//    }


    private fun setCategoriesSpinner() {
        categoriesList.clear()
        categoriesList.add(" All Categories")


        db.child("AllCategories").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                for(document in p0.children){
                    categoriesList.add(document.value.toString());
                }

                spinner_ByCategories.adapter =
                    ArrayAdapter(requireContext(), R.layout.layout_spinnner, categoriesList)

                spinner_ByCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {


                        if(categoriesList[position] == " All Categories"){

                            Toast.makeText(context, "Getting Data For All Categories", Toast.LENGTH_SHORT).show()
                        }else{
                            db.child("SubCategories").child(categoriesList[position]).addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {}

                                override fun onDataChange(p0: DataSnapshot) {
                                    for(document in p0.children){
                                        Toast.makeText(context, ""+document.value.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }

                    }
                }

            }
        })



    }

}