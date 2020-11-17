package com.sagheerhussainzardari.cheflab.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.cheflab.Adapters.SearchDishesAdapter
import com.sagheerhussainzardari.cheflab.MainActivity
import com.sagheerhussainzardari.cheflab.Models.DishesModel
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.fragment_search_dish.*

class SearchDishFragment : Fragment() {

    companion object {
        var dishesList = ArrayList<DishesModel>()
        var filteredList = ArrayList<DishesModel>()
        var dishesNames = ArrayList<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_dish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseDatabase.getInstance().getReference("AllDishes")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    dishesList.clear()
                    var len = p0.childrenCount
                    for (dish in p0.children) {
                        len -= 1
                        dishesNames.add(dish.key.toString())
                        dishesList.add(
                            DishesModel(
                                dish.key.toString(),
                                dish.child("dish_img").value.toString(),
                                dish.child("ingredents").value.toString(),
                                dish.child("cooking_method").value.toString(),
                                dish.child("duration").value.toString(),
                                dish.child("dish_video").value.toString(),
                                ""
                            )
                        )

                        if (len.toString() == "0") {
                            dishesList
                            setUpRecycler()
                        }
                    }
                }
            })


        et_searchDish.addTextChangedListener {

            var len = dishesList.size
            filteredList.clear()
            for (item in dishesList) {
                len -= 1
                if (item.dishName.toLowerCase().contains("" + et_searchDish.text.toString())) {
                    filteredList.add(item)
                }
                if (len.toString() == "0") {
                    setUpRecyclerWithFilterd(filteredList)
                }
            }

        }


    }

    private fun setUpRecyclerWithFilterd(filteredList: ArrayList<DishesModel>) {
        rv_TotalDishes.setHasFixedSize(true)
        rv_TotalDishes.layoutManager = GridLayoutManager(context, 1)
        rv_TotalDishes.adapter =
            SearchDishesAdapter(requireContext(), filteredList, this)
    }


    fun setUpRecycler() {

        rv_TotalDishes.setHasFixedSize(true)
        rv_TotalDishes.layoutManager = GridLayoutManager(context, 1)
        rv_TotalDishes.adapter =
            SearchDishesAdapter(requireContext(), dishesList, this)
    }


    fun onCardClicked(dishItem: DishesModel) {
        MatchingDishesFragment.currentSelectedDish = dishItem
        (activity as MainActivity).openDetailDish()
    }


}