package com.mahmoodahmedpahnwar.cheflab.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoodahmedpahnwar.cheflab.Fragments.ScanIngredentsFragment
import com.mahmoodahmedpahnwar.cheflab.Fragments.ScanIngredentsFragment.Companion.lablesList
import com.mahmoodahmedpahnwar.cheflab.Models.IngredentsModel
import com.mahmoodahmedpahnwar.cheflab.R
import kotlinx.android.synthetic.main.recycler_layout_list_of_sub_cat.view.*

class ListScannedIngredentsAdapter(
    var context: Context,
    var listOfIngredents: ArrayList<IngredentsModel>,
    var scanFragment: ScanIngredentsFragment
) :
    RecyclerView.Adapter<ListScannedIngredentsAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_layout_list_of_sub_cat, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listOfIngredents.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//
        holder.view.checkbox.setOnClickListener {
            scanFragment.onItemChecked(listOfIngredents[position].ingredentName)
        }
//        if (booksList[position].bookRatings == "0.0") {
//            holder.view.tv_BookRatings.visibility = View.GONE
//        }

        if (lablesList.contains(listOfIngredents[position].ingredentName)) {
            holder.view.checkbox.isChecked = true
        }

        holder.view.tv_ingredentName.text = listOfIngredents[position].ingredentName
    }
}