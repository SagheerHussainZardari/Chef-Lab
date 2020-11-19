package com.mahmoodahmedpahnwar.cheflab.Fragments

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
import com.mahmoodahmedpahnwar.cheflab.Adapters.ShoppingListAdapter
import com.mahmoodahmedpahnwar.cheflab.Models.IngredentsModel
import com.mahmoodahmedpahnwar.cheflab.R
import com.mahmoodahmedpahnwar.cheflab.toastshort
import kotlinx.android.synthetic.main.fragment_shopping_cart.*

class ShoppingCartFragment : Fragment() {

    companion object {
        var listOfIngredents = ArrayList<IngredentsModel>()
        val ref = FirebaseDatabase.getInstance().getReference("ShoppingIngredents").child(
            FirebaseAuth.getInstance().currentUser?.uid.toString()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListAndRefreshList()

    }

    private fun getListAndRefreshList() {


        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                listOfIngredents.clear()
                var len = p0.childrenCount
                for (item in p0.children) {
                    len -= 1
                    listOfIngredents.add(IngredentsModel(item.value.toString()))

                    if (len.toString() == "0") {
                        setUpRecycler()
                    }
                }
            }
        })
    }

    fun setUpRecycler() {

        rv_shoppingList.setHasFixedSize(true)
        rv_shoppingList.layoutManager = GridLayoutManager(context, 1)
        rv_shoppingList.adapter =
            ShoppingListAdapter(requireContext(), listOfIngredents, this)
    }

    fun onItemChecked(ingredentName: String) {
        ref.child(ingredentName).removeValue()
        listOfIngredents.remove(IngredentsModel(ingredentName))
        setUpRecycler()
        context?.toastshort("Item Removed From Shopping Cart!!!")
//        getListAndRefreshList()

    }

}