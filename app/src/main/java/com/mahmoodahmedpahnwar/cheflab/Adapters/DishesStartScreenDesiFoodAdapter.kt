package com.mahmoodahmedpahnwar.cheflab.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoodahmedpahnwar.cheflab.Fragments.FirstScreen
import com.mahmoodahmedpahnwar.cheflab.Models.DishWithPicture
import com.mahmoodahmedpahnwar.cheflab.R
import com.mahmoodahmedpahnwar.cheflab.toastlong
import kotlinx.android.synthetic.main.recycler_view_start_screen.view.*


class DishesStartScreenDesiFoodAdapter(
    var context: Context,
    var desiFoodList: ArrayList<DishWithPicture>,
    var first_screen: FirstScreen
) :
    RecyclerView.Adapter<DishesStartScreenDesiFoodAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_start_screen, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return desiFoodList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.view.iv_dishImage_start_screen.setOnClickListener {
            context.toastlong("These Dishes Are Only For Display Please Press Camera Button To Scan For Ingredients")
        }
        holder.view.tv_dish_name_start_screen.text = desiFoodList[position].dishName

        Glide.with(context).load(desiFoodList[position].dishImage)
            .into(holder.view.iv_dishImage_start_screen)
    }
}