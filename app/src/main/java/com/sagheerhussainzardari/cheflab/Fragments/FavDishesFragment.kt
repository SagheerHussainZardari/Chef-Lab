package com.sagheerhussainzardari.cheflab.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.cheflab.Adapters.FavDishesAdapter
import com.sagheerhussainzardari.cheflab.R
import com.sagheerhussainzardari.cheflab.toastlong
import com.sagheerhussainzardari.cheflab.toastshort
import kotlinx.android.synthetic.main.fragment_fav_dishes.*


class FavDishesFragment : Fragment() {

    companion object {
        var list = ArrayList<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_dishes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var db = FirebaseDatabase.getInstance().getReference("FavDishes")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())

        btn_clearFavList.setOnClickListener {
            db.removeValue().addOnCompleteListener {
                context?.toastshort("All Favourite Dishes Cleared!!!")
            }
        }



        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {

                var length = p0.childrenCount

                if (length.toString() == "0") {
                    context?.toastlong("No Favourite Dishes Found!!!")
                }
                for (item in p0.children) {
                    length -= 1
                    var dish = item.value.toString()

                    list.add(dish)

                    if (length.toString() == "0") {
                        openHomeFragemnt()

                    }
                }

            }
        })
    }

    private fun openHomeFragemnt() {

        rv_favDishes.setHasFixedSize(true)
        rv_favDishes.layoutManager = GridLayoutManager(context, 1)
        rv_favDishes.adapter =
            FavDishesAdapter(requireContext(), list, this)

    }
}