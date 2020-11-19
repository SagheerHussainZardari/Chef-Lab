package com.mahmoodahmedpahnwar.cheflab.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoodahmedpahnwar.cheflab.Fragments.FavDishesFragment
import com.mahmoodahmedpahnwar.cheflab.R
import kotlinx.android.synthetic.main.recycler_fav_dishes.view.*

class FavDishesAdapter(
    var context: Context,
    var listOfDishes: ArrayList<String>,
    var FavDishFragment: FavDishesFragment
) :
    RecyclerView.Adapter<FavDishesAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_fav_dishes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listOfDishes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_dishName_Fav.text = listOfDishes[position]
    }
}