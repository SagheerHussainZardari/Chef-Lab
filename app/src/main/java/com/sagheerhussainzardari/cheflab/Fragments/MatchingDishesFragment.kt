package com.sagheerhussainzardari.cheflab.Fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.cheflab.Adapters.DishesAdapter
import com.sagheerhussainzardari.cheflab.MainActivity
import com.sagheerhussainzardari.cheflab.Models.DishesModel
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.fragment_matched_dishes.*

class MatchingDishesFragment : Fragment() {

    companion object {


        var db = FirebaseDatabase.getInstance().reference
        var dishesList = ArrayList<DishesModel>()
        var currentSelectedDish: DishesModel? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matched_dishes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//            context?.toastshort(HomeFragment.currentIngredents.toString())
        progressbar_DeleteBook.visibility = View.VISIBLE

        getAllDishes()
    }

    private fun getAllDishes() {
        db.child("AllDishes").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                dishesList.clear()
                for (document in p0.children) {

                    //ANY OF INGREDENTS FOUNT
                    var dishName = document.key.toString()
                    var newList = document.child("ingredents").value.toString().split(',')
                    var newListTrimed = ArrayList<String>()
                    for (item in newList) {
                        newListTrimed.add(item.trim())
                    }
                    var doFilter = false
                    for (item in newListTrimed) {
                        for (ingredent in HomeFragment.currentIngredents) {
                            if (item == ingredent) {
                                doFilter = true
                            }
                        }
                    }
                    var remaining_items = ArrayList<String>()
                    for (item in newListTrimed) {
//                        [sugar , milk , water , tea]
                        var itemFound = false
                        for (ingredent in HomeFragment.currentIngredents) {
//                            [sugar]
                            if (item == ingredent) {
                                itemFound = true
                            }
                        }
                        if (!itemFound) {
                            remaining_items.add(item)
                        }
                    }
                    if (doFilter) {
                        dishesList.add(
                            DishesModel(
                                dishName,
                                document.child("dish_img").value.toString(),
                                newListTrimed.toString(),
                                document.child("cooking_method").value.toString(),
                                document.child("duration").value.toString(),
                                document.child("dish_video").value.toString(),
                                remaining_items.toString()
                            )
                        )
                    }


                    //ALL INGREDENTS FOUND

//                    var dishName = document.key.toString()
//                    var newList = document.child("ingredents").value.toString().split(',')
//                    var newListTrimed = ArrayList<String>()
//                    for (item in newList) {
//                        newListTrimed.add(item.trim())
//                    }
//                    var doFilter = true;
//                    for (ingredent in HomeFragment.currentIngredents) {
//                        var thisItemThere = false;
//                        for (item in newListTrimed) {
//                            if (item == ingredent) {
//                                thisItemThere  = true;
//                            }
//                        }
//                        if(!thisItemThere){
//                            doFilter = false;
//                        }
//                    }
//                    if (doFilter) {
//                        dishesList.add(DishesModel(dishName))
//                    }
                }

                tv_TotalDishedFound.text = dishesList.size.toString()
                setUpRecyclerView(dishesList)
            }
        })
    }


    fun onVideoIconClicked(dishVideo: String) {
        if (dishVideo != "" && dishVideo != "null") {
            val intentBrowser = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(dishVideo)
            )
            try {
                context?.startActivity(intentBrowser)
            } catch (ex: ActivityNotFoundException) {
            }
        }
    }

    private fun setUpRecyclerView(dishesList: ArrayList<DishesModel>) {
        recyclerview_matched_dishes_list.setHasFixedSize(true)
        recyclerview_matched_dishes_list.layoutManager = GridLayoutManager(context, 1)
        recyclerview_matched_dishes_list.adapter =
            DishesAdapter(requireContext(), dishesList, this)

        progressbar_DeleteBook.visibility = View.GONE
    }

    fun onCardClicked(dishItem: DishesModel) {
        currentSelectedDish = dishItem
        (activity as MainActivity).openDetailDish()
    }


}