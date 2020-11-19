package com.mahmoodahmedpahnwar.cheflab.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mahmoodahmedpahnwar.cheflab.Adapters.DishesStartScreenDesiFoodAdapter
import com.mahmoodahmedpahnwar.cheflab.MainActivity
import com.mahmoodahmedpahnwar.cheflab.Models.DishWithPicture
import com.mahmoodahmedpahnwar.cheflab.R
import kotlinx.android.synthetic.main.fragment_first_screen.*

class FirstScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setUpDesiRecycler()
        setUpPakistaniRecycler()
        setUpChineeseRecycler()


        fab_scanForIngredents.setOnClickListener {
            (activity as MainActivity).openScanner()
        }

    }


    private fun setUpDesiRecycler() {
        var list = ArrayList<DishWithPicture>()
        list.add(
            DishWithPicture(
                "Qorma",
                "https://i.ndtvimg.com/i/2016-10/chicken-korma_650x400_51475662188.jpg"
            )
        )
        list.add(
            DishWithPicture(
                "Karhai",
                "https://www.masala.tv/wp-content/uploads/2019/12/Masala-karhai-Mehboob-Kitchen.jpg"
            )
        )
        list.add(
            DishWithPicture(
                "Egg Sabzi",
                "https://i.ytimg.com/vi/Ky7CAA8VozE/maxresdefault.jpg"
            )
        )
        list.add(
            DishWithPicture(
                "Samosa",
                "https://timesofindia.indiatimes.com/thumb/msid-45824113,width-1200,height-900,resizemode-4/.jpg"
            )
        )

        rv_desiFood.setHasFixedSize(true)
        rv_desiFood.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        rv_desiFood.adapter =
            DishesStartScreenDesiFoodAdapter(requireContext(), list, this)
    }


    private fun setUpPakistaniRecycler() {

        var list = ArrayList<DishWithPicture>()
        list.add(
            DishWithPicture(
                "Halwa Puri",
                "https://i.pinimg.com/originals/99/87/ed/9987edf5134241bf78d2a8fc5c038bca.jpg"
            )
        )
        list.add(
            DishWithPicture(
                "Seiji",
                "https://www.foodaholic.biz/wp-content/uploads/2013/06/PhotoEditor-15221361427481.jpg"
            )
        )

        list.add(
            DishWithPicture(
                "Biryani and Pulao",
                "https://static.toiimg.com/thumb/60324819.cms?width=1200&height=900"
            )
        )
        list.add(
            DishWithPicture(
                "Charga",
                "https://i1.wp.com/www.simpleglutenfreekitchen.com/wp-content/uploads/2018/10/Lahori-Chicken-Chargha-700x466.jpg?resize=700%2C466"
            )
        )
        list.add(
            DishWithPicture(
                "Lassi Yogurt Drink",
                "https://lh3.googleusercontent.com/6K7O_ZVvT85UqzilQytf7HAZ7dJCZhJra9F-ERMzxgtgWnNhki89Ktq2nEGFr6Mm3pVyyt7O3Cy9W0QwgK2Obfe_ti0Ld-hBgnHMfAA=w600-rj-l68-e365"
            )
        )

        rv_pakistaniFood.setHasFixedSize(true)
        rv_pakistaniFood.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        rv_pakistaniFood.adapter =
            DishesStartScreenDesiFoodAdapter(requireContext(), list, this)
    }

    private fun setUpChineeseRecycler() {

        var list = ArrayList<DishWithPicture>()
        list.add(
            DishWithPicture(
                "Chow Mein",
                "https://butteroverbae.com/wp-content/uploads/2019/02/easy-chicken-chow-mein-recipe-10.jpg"
            )
        )

        list.add(
            DishWithPicture(
                "Shrimp",
                "https://i1.wp.com/www.foodrepublic.com/wp-content/uploads/2011/04/drunken-shrimp.jpg?fit=700%2C524&ssl=1"
            )
        )
        list.add(
            DishWithPicture(
                "Hotpot",
                "https://news.cgtn.com/news/3d3d514d3551544d31457a6333566d54/img/d28dd1786f3e4bb598da6e577b369898/d28dd1786f3e4bb598da6e577b369898.jpg"
            )
        )
        list.add(
            DishWithPicture(
                "Steamed Vermicelli Rolls",
                "https://i.pinimg.com/236x/8d/8b/e8/8d8be8b0ee63cf8ab7f58a867e9d9f9c--china.jpg"
            )
        )
        list.add(
            DishWithPicture(
                "Dumplings",
                "https://data.thefeedfeed.com/static/2020/03/16/15843884505e6fd962961e3.jpg"
            )
        )

        rv_chineeseFood.setHasFixedSize(true)
        rv_chineeseFood.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        rv_chineeseFood.adapter =
            DishesStartScreenDesiFoodAdapter(requireContext(), list, this)

    }


}