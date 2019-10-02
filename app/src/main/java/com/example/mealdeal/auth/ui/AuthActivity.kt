package com.example.mealdeal.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth


import android.view.View

import android.app.Activity
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse



class AuthActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser?=null
    private lateinit var providers:List<AuthUI.IdpConfig>
    private var RC_SIGN_IN=123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mealdeal.R.layout.activity_auth)

        mAuth = FirebaseAuth.getInstance()
        mAuth = FirebaseAuth.getInstance()


       signIn()
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




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }


    fun onClick(v: View) {
        when (v.getId()) {
            com.example.mealdeal.R.id.sign_in_button -> signIn()
        }

    }


}



