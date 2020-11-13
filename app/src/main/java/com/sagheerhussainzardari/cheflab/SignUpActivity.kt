package com.sagheerhussainzardari.cheflab

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.layout_signup.*


class SignUpActivity : AppCompatActivity() {

    companion object {
        var mAuth = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().reference
        var username = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUpSignUp.setOnClickListener {
            if (isInternetAvaiable())
                signUp()
            else
                toastlong("Please Check Your Internet Connection!!!\nTry Again!!!")
        }
    }

    private fun isInternetAvaiable(): Boolean {
        val c = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return c.activeNetworkInfo != null && c.activeNetworkInfo.isConnected
    }

    private fun signUp() {

        val name = et_NameSignUp.text.toString().trim()
        val email = et_EmailSignUp.text.toString().trim()
        val phone = et_PhoneSignUp.text.toString().trim()
        val city = et_CitySignUp.text.toString().trim()
        val address = et_AddressSignUp.text.toString().trim()
        val password = et_PasswordSignUp.text.toString().trim()
        val passwordConfirm = et_PasswordConfirmSignUp.text.toString().trim()
        username = name

        if (name.isEmpty()) {
            et_NameSignUp.error = "Field Can Not Be Empty!!!"
            et_NameSignUp.requestFocus()
            return
        }
        if (email.isEmpty()) {
            et_EmailSignUp.error = "Field Can Not Be Empty!!!"
            et_EmailSignUp.requestFocus()
            return
        }
        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            et_EmailSignUp.error = "Enter A Valid Email!!!"
            et_EmailSignUp.requestFocus()
            return
        }
        if (phone.isEmpty()) {
            et_PhoneSignUp.error = "Field Can Not Be Empty!!!"
            et_PhoneSignUp.requestFocus()
            return
        }

        if (phone.trim().length != 11 || !(Patterns.PHONE.matcher(phone).matches())) {
            et_PhoneSignUp.error = "Enter A Valid Phone Number!!\neg: 03063092274"
            et_PhoneSignUp.requestFocus()
            return
        }

        if (city.isEmpty()) {
            et_CitySignUp.error = "Field Can Not Be Empty!!!"
            et_CitySignUp.requestFocus()
            return
        }

        if (address.isEmpty()) {
            et_AddressSignUp.error = "Field Can Not Be Empty!!!"
            et_AddressSignUp.requestFocus()
            return
        }
        if (password.isEmpty()) {
            et_PasswordSignUp.error = "Field Can Not Be Empty!!!"
            et_PasswordSignUp.requestFocus()
            return
        }
        if (passwordConfirm.isEmpty()) {
            et_PasswordConfirmSignUp.error = "Field Can Not Be Empty!!!"
            et_PasswordConfirmSignUp.requestFocus()
            return
        }
        if (password == passwordConfirm) {
            progressBarLayoutSignUpAcitivity.visibility = View.VISIBLE

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    db.child("Users").child(mAuth.currentUser!!.uid).child("name").setValue(name)
                    db.child("Users").child(mAuth.currentUser!!.uid).child("email").setValue(email)
                    db.child("Users").child(mAuth.currentUser!!.uid).child("phone").setValue(phone)
                    db.child("Users").child(mAuth.currentUser!!.uid).child("city").setValue(city)
                    db.child("Users").child(mAuth.currentUser!!.uid).child("address")
                        .setValue(address)

                    progressBarLayoutSignUpAcitivity.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    progressBarLayoutSignUpAcitivity.visibility = View.GONE
                    toastshort("SignUp Failed\n${it.exception!!.localizedMessage}")
                }
            }

        } else {
            et_PasswordConfirmSignUp.error = "Passwords Must Match!!!"
            et_PasswordConfirmSignUp.requestFocus()
        }

    }


}