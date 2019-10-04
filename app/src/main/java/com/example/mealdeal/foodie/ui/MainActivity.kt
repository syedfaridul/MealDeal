package com.example.mealdeal.foodie

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.mealdeal.R

import com.example.mealdeal.auth.ui.AuthActivity
import com.example.mealdeal.util.FirebaseUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.client.AuthUiInitProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    // Write a message to the database

    private lateinit var database: DatabaseReference
    private var mMessageReference: DatabaseReference? = null
    private var mUser: FirebaseUser?=null
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener


    // Firebase instance variables
    private lateinit var mAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser?=null
    private lateinit var providers:List<AuthUI.IdpConfig>
    private var RC_SIGN_IN=123



// ...


    // var myRef:DatabaseReference=database.getReference("message")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mealdeal.R.layout.activity_main)

        database = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        mAuth = FirebaseAuth.getInstance()


        database!!.child("title").setValue("JavaSampleApproach")


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(com.example.mealdeal.R.menu.save_menu,menu)

        return true
    }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

           R.id.save_menu ->{
                 saveMenu()
                 return true
             }

           R.id.sign_out_menu->{


                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


/*
    override fun onPause() {
        super.onPause()
        detachListener()
    }


    fun attachListener() {
        mAuth.addAuthStateListener(mAuthListener)
    }

    fun detachListener() {
        mAuth.removeAuthStateListener(mAuthListener)
    }


    override fun onResume() {
        super.onResume()
        attachListener()
    }
*/

    fun saveMenu(){

    }
}
