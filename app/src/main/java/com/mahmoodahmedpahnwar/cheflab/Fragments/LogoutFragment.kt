package com.mahmoodahmedpahnwar.cheflab.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.mahmoodahmedpahnwar.cheflab.LoginActivity
import com.mahmoodahmedpahnwar.cheflab.R

class LogoutFragment : Fragment() {

    companion object {
        var mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }


    override fun onResume() {
        super.onResume()
        mAuth.signOut()
        startActivity(Intent(context, LoginActivity::class.java))
    }
}

