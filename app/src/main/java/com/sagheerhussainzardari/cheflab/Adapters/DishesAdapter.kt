package com.sagheerhussainzardari.cheflab.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sagheerhussainzardari.cheflab.Fragments.MatchingDishesFragment
import com.sagheerhussainzardari.cheflab.Models.DishesModel
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.recycler_layout_matched_dishes_list.view.*


class DishesAdapter(
    var context: Context,
    var dishesList: ArrayList<DishesModel>,
    var booksForSellFragment: MatchingDishesFragment
) :
    RecyclerView.Adapter<DishesAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_layout_matched_dishes_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dishesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.dish_card.setOnClickListener {

            booksForSellFragment.onCardClik(dishesList[position].dishVideo)

        }

        holder.view.tv_dish_name.text = dishesList[position].dishName
        holder.view.tv_ingredents.text = "Ingredents: " + (dishesList[position].dishIngredents)


        holder.view.tv_duration.text = "Duration: " + dishesList[position].dishDuration
        holder.view.tv_ingredentsRemaining.text =
            "Remaing Ingredients: " + dishesList[position].dishRemaingIngredents
        holder.view.tv_cookingMethod.text =
            "Cooking Method: " + dishesList[position].dishCookingMethod

        if (dishesList[position].dishVideo != "" && dishesList[position].dishVideo != "null") {
            holder.view.iv_videoLogo.visibility = View.VISIBLE
        } else {
            holder.view.iv_videoLogo.visibility = View.GONE
        }
        Glide.with(context).load(dishesList[position].dishImgUrl).into(holder.view.iv_dishImage)
    }


}


