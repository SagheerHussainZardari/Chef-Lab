package com.sagheerhussainzardari.cheflab.Fragments

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
import com.sagheerhussainzardari.cheflab.Models.DishesModel
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.fragment_matched_dishes.*

class MatchingDishesFragment : Fragment() {

    companion object {
        var db = FirebaseDatabase.getInstance().reference
        var dishesList = ArrayList<DishesModel>()
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
                    dishesList.add(DishesModel("" + document.value.toString()))
                }

                setUpRecyclerView(dishesList)
            }
        })
    }

    private fun setUpRecyclerView(dishesList: ArrayList<DishesModel>) {
        recyclerview_matched_dishes_list.setHasFixedSize(true)
        recyclerview_matched_dishes_list.layoutManager = GridLayoutManager(context, 1)
        recyclerview_matched_dishes_list.adapter =
            DishesAdapter(requireContext(), dishesList, this)

        progressbar_DeleteBook.visibility = View.GONE
    }


}