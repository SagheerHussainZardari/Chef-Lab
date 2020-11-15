package com.sagheerhussainzardari.cheflab.Fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sagheerhussainzardari.cheflab.R
import kotlinx.android.synthetic.main.fragment_dish_detail.*

class DishDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            Glide.with(it).load(MatchingDishesFragment.currentSelectedDish?.dishImgUrl)
                .into(iv_dImage)
        }

        var dishVideo = MatchingDishesFragment.currentSelectedDish?.dishVideo.toString()

        if (dishVideo != "" && dishVideo != "null") {
            btn_playVideo.visibility = View.VISIBLE
        } else {
            btn_playVideo.visibility = View.GONE
        }


        btn_playVideo.setOnClickListener {
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

        tv_ingredentsAvaiable.text =
            MatchingDishesFragment.currentSelectedDish?.dishIngredents.toString()
        tv_ingredentsRemaing.text =
            MatchingDishesFragment.currentSelectedDish?.dishRemaingIngredents.toString()
        tv_cookingMethod.text =
            MatchingDishesFragment.currentSelectedDish?.dishCookingMethod.toString()
        tv_duration.text =
            MatchingDishesFragment.currentSelectedDish?.dishDuration.toString()
    }

}