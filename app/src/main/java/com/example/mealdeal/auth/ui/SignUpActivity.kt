package com.example.mealdeal.auth.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mealdeal.R
import com.example.mealdeal.auth.vmfactory.AuthViewModel
import com.example.mealdeal.foodie.ui.MainActivity
import com.example.mealdeal.util.AuthListener
import com.example.mealdeal.util.startHomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.progressbar
import kotlinx.android.synthetic.main.activity_sign_up.text_view_register
import javax.inject.Inject

class SignUpActivity : DaggerAppCompatActivity(),AuthListener {


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var mAuth: FirebaseAuth
    lateinit var viewModel: AuthViewModel
    private var mUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        viewModel.authListener= this
        initView()
    }

    private fun initView(){

      //  viewModel.email=

        button_sign_in.setOnClickListener {
                v: View? ->
            viewModel.email=text_email_signup.toString()
            viewModel.password=edit_text_password_signup.toString()
            viewModel.signup()
        }

        text_view_register.setOnClickListener { v: View? ->
            viewModel.goToLogin(v!!)
        }



    }

    override fun onStarted() {
        progressbar.visibility = View.VISIBLE
        Intent(this, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        startHomeActivity()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}