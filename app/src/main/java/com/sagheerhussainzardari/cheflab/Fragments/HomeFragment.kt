package com.sagheerhussainzardari.cheflab.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.cheflab.Adapters.ListOfIngredentsAdapter
import com.sagheerhussainzardari.cheflab.MainActivity
import com.sagheerhussainzardari.cheflab.Models.IngredentsModel
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        var booksList = ArrayList<IngredentsModel>()
        var db = FirebaseDatabase.getInstance().reference

        var categoriesList = ArrayList<String>()
        var listOfIngredents = ArrayList<IngredentsModel>()
        var booksListFiltered = ArrayList<IngredentsModel>()
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
        progressBarLayoutHomeFragment.visibility = View.VISIBLE

        (activity as MainActivity).setUsersDetails()

        setCategoriesSpinner()

        progressBarLayoutHomeFragment.setOnClickListener { }

    }


    private fun setCategoriesSpinner() {
        categoriesList.clear()
        categoriesList.add(" All Categories")


        db.child("AllCategories").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                for(document in p0.children){
                    categoriesList.add(document.value.toString())
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
                        progressBarLayoutHomeFragment.visibility = View.VISIBLE

                        if (categoriesList[position] == " All Categories") {
                            Toast.makeText(
                                context,
                                "Getting Data For All Categories",
                                Toast.LENGTH_SHORT
                            ).show()

                            db.child("SubCategories")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {}

                                    override fun onDataChange(p0: DataSnapshot) {

                                        listOfIngredents.clear()
                                        for (document in p0.children) {
                                            for (item in p0.child(document.key.toString()).children) {
                                                listOfIngredents.add(IngredentsModel(item.value.toString()))
                                            }
                                        }
                                        setUpRecyclerView(listOfIngredents)
                                    }
                                })

                        }else{
                            db.child("SubCategories").child(categoriesList[position]).addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {}

                                override fun onDataChange(p0: DataSnapshot) {
                                    listOfIngredents.clear()
                                    for (document in p0.children) {
                                        listOfIngredents.add(IngredentsModel(document.value.toString()))
                                    }
//                                    Toast.makeText(context, ""+ listOfIngredents , Toast.LENGTH_SHORT).show()

                                    setUpRecyclerView(listOfIngredents)
                                }
                            })
                        }

                    }
                }

            }
        })


    }

    private fun setUpRecyclerView(listOfIngredents: ArrayList<IngredentsModel>) {
        recyclerview_list_of_sub_cats.setHasFixedSize(true)
        recyclerview_list_of_sub_cats.layoutManager = GridLayoutManager(context, 1)
        recyclerview_list_of_sub_cats.adapter =
            ListOfIngredentsAdapter(requireContext(), listOfIngredents, this)

        progressBarLayoutHomeFragment.visibility = View.GONE

    }

}