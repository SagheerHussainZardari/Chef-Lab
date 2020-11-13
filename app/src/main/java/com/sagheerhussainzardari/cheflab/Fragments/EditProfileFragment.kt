package com.sagheerhussainzardari.cheflab.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.cheflab.*
import kotlinx.android.synthetic.main.fragment_editprofile.*

class EditProfileFragment : Fragment() {

    companion object {
        var mAuth = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("Users")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_editprofile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        db.child(mAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    et_Name_UpdateProfile.setText(p0.child("name").value.toString())
                    et_Phone_UpdateProfile.setText(p0.child("phone").value.toString())
                    et_City_UpdateProfile.setText(p0.child("city").value.toString())
                    et_Address_UpdateProfile.setText(p0.child("address").value.toString())

                    btn_UpdateProfile.isEnabled = true
                }
            })

        btn_UpdateProfile.setOnClickListener {
            if (isInternetAvaiable())
                updateProfile()
            else
                context?.toastlong("Internet Connection Not Avaiable...\nTry Again...")
        }
    }

    private fun updateProfile() {


        val name = et_Name_UpdateProfile.text.toString().trim()
        val phone = et_Phone_UpdateProfile.text.toString().trim()
        val city = et_City_UpdateProfile.text.toString().trim()
        val address = et_Address_UpdateProfile.text.toString().trim()


        if (name.isEmpty()) {
            et_Name_UpdateProfile.error = "Field Must Not Be Empty!!!"
            et_Name_UpdateProfile.requestFocus()
            return
        }
        if (phone.isEmpty()) {
            et_Phone_UpdateProfile.error = "Field Must Not Be Empty!!!"
            et_Phone_UpdateProfile.requestFocus()
            return
        }
        if (phone.trim().length != 11 || !(Patterns.PHONE.matcher(phone).matches())) {
            et_Phone_UpdateProfile.error = "Enter A Valid Phone Number!!\neg: 03063092274"
            et_Phone_UpdateProfile.requestFocus()
            return
        }


        if (city.isEmpty()) {
            et_City_UpdateProfile.error = "Field Must Not Be Empty!!!"
            et_City_UpdateProfile.requestFocus()
            return
        }
        if (address.isEmpty()) {
            et_Address_UpdateProfile.error = "Field Must Not Be Empty!!!"
            et_Address_UpdateProfile.requestFocus()
            return
        }

        SignUpActivity.db.child("Users").child(SignUpActivity.mAuth.currentUser!!.uid).child("name")
            .setValue(name)
        SignUpActivity.db.child("Users").child(SignUpActivity.mAuth.currentUser!!.uid)
            .child("phone").setValue(phone)
        SignUpActivity.db.child("Users").child(SignUpActivity.mAuth.currentUser!!.uid).child("city")
            .setValue(city)
        SignUpActivity.db.child("Users").child(SignUpActivity.mAuth.currentUser!!.uid)
            .child("address").setValue(address).addOnCompleteListener {
                if (it.isSuccessful) {
                    (activity as MainActivity).setUsersDetails()
                    context?.toastshort("Profile Updated Successfully...")
                } else {
                    context?.toastshort("Something Went Wrong...\nTry Again Later...")
                }
            }
    }


    private fun isInternetAvaiable(): Boolean {
        val c = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return c.activeNetworkInfo != null && c.activeNetworkInfo.isConnected
    }


}