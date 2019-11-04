package com.example.mealdeal.foodie

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.mealdeal.R
import com.example.mealdeal.foodie.ui.MainActivity

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class LoginActivity : AppCompatActivity(){

    private lateinit var mAuth: FirebaseAuth
    private var mUser: FirebaseUser?=null
    private val RC_SIGN_IN = 1

    private val signInProviders =
        listOf(AuthUI.IdpConfig.EmailBuilder()
            .setAllowNewAccounts(true)
            .setRequireName(true)
            .build())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        account_sign_in.setOnClickListener {
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders)
                .setLogo(R.drawable.ic_splash_background)
                .build()
            startActivityForResult(intent, RC_SIGN_IN)
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = IdpResponse.fromResultIntent(data)

        if (requestCode == RC_SIGN_IN) {



            if (resultCode == Activity.RESULT_OK) {
                val progressDialog = indeterminateProgressDialog("Setting up your account")

                    startActivity(intentFor<MainActivity>().newTask().clearTask())



                    progressDialog.dismiss()
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) return

                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK ->
                        Snackbar.make(constraint_layout, "No Network",
                            Snackbar.LENGTH_LONG).show()
                    ErrorCodes.UNKNOWN_ERROR ->
                        Snackbar.make(constraint_layout, "Unknown Error",
                            Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }







