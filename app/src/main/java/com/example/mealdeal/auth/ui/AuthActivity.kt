package com.example.mealdeal.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth


import android.view.View

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mealdeal.R
import com.example.mealdeal.auth.vmfactory.AuthViewModel
import com.example.mealdeal.foodie.ui.MainActivity
import com.example.mealdeal.util.AuthListener
import com.example.mealdeal.util.startHomeActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject


class AuthActivity : DaggerAppCompatActivity(),AuthListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var mAuth: FirebaseAuth
    lateinit var viewModel: AuthViewModel
    private var mUser: FirebaseUser?=null
    var RC_SIGN_IN= 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mealdeal.R.layout.activity_auth)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        viewModel.authListener= this
        // initView()*/
        signIn()

    }


   private fun initView(){


       button_sign_in.setOnClickListener {
           v: View? ->
           viewModel.email= text_email.text.toString()
           viewModel.password=text_view_register.text.toString()

           viewModel.login()

       }

       text_view_register.setOnClickListener { v: View? ->
           viewModel.goToSignup(v!!)
       }


   }



   private fun signIn() {

        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                mUser = FirebaseAuth.getInstance().currentUser
              Intent(this, MainActivity::class.java).also {
                  startActivity(intent)
              }
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

        }*/




   override fun onStarted() {
        progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        startHomeActivity()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            startHomeActivity()
        }
    }
}





