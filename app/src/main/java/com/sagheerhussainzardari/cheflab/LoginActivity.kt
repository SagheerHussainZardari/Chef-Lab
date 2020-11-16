package com.sagheerhussainzardari.cheflab

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_connectedtointernet.*
import kotlinx.android.synthetic.main.layout_login.*
import kotlinx.android.synthetic.main.layout_splash.*

class LoginActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLoginLogin.setOnClickListener {
            login()
        }

        tv_ForgotPasswordLogin.setOnClickListener {
            forgotPassword()
        }

        btnSignUpLogin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnRetry.setOnClickListener {
            onRetryButtonClicked()
        }

    }

    private fun login() {

        val email = et_EmailLogin.text.toString().trim()
        val password = et_PasswordLogin.text.toString().trim()

        if (email.isEmpty()) {
            et_EmailLogin.error = "Email Can Not Be Empty!!!"
            return
        }
        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            et_EmailLogin.error = "Enter A Valid Email Address Please!!!"
            return
        }
        if (password.isEmpty()) {
            et_PasswordLogin.error = "Password Can Not Be Empty!!!"
            return
        }

        progressBarLayoutLoginAcitivity.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                progressBarLayoutLoginAcitivity.visibility = View.GONE
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                progressBarLayoutLoginAcitivity.visibility = View.GONE
                toastshort("Login Failed...\n${it.exception!!.localizedMessage}")
            }
        }
    }

    private fun forgotPassword() {
        val email = et_EmailLogin.text.toString().trim()

        if (email.isEmpty()) {
            et_EmailLogin.error = "Email Can Not Be Empty!!!"
            return
        }

        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            et_EmailLogin.error = "Enter A Valid Email Address Please!!!"
            return
        }

        progressBarLayoutLoginAcitivity.visibility = View.VISIBLE

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                progressBarLayoutLoginAcitivity.visibility = View.GONE
                toastlong("Reset Password Link Sent To Your Email Check Inbox!!")
            } else {
                progressBarLayoutLoginAcitivity.visibility = View.GONE
                toastshort("Failded...\n${it.exception!!.localizedMessage}")
            }
        }
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }

    override fun onStart() {
        super.onStart()

        if (isInternetAvaiable()) {
            internet_connection_layout.visibility = View.GONE
            if (mAuth.currentUser != null) {
                splash.visibility = View.GONE
                startActivity(Intent(this, MainActivity::class.java))

            } else {
                splash.visibility = View.VISIBLE
                logolayout.animate().scaleXBy(1f).scaleYBy(1f).setDuration(2500).start()
                splash.setOnClickListener { }
                Handler().postDelayed({
                    splash.visibility = View.GONE
                }, 3500)
            }

        } else {
            internet_connection_layout.visibility = View.VISIBLE
        }
    }
    private fun onRetryButtonClicked() {
        onStart()
    }

    private fun isInternetAvaiable(): Boolean {
        val c = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return c.activeNetworkInfo != null && c.activeNetworkInfo.isConnected
    }
}