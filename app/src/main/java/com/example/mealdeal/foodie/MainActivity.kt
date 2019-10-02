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
import androidx.core.content.ContextCompat.startActivity

import com.example.mealdeal.auth.ui.AuthActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.client.AuthUiInitProvider


class MainActivity : AppCompatActivity() {

    // Write a message to the database

    private lateinit var database: DatabaseReference
    private var mMessageReference: DatabaseReference? = null

    // Firebase instance variables
    private lateinit var mAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser?=null
    private lateinit var providers:List<AuthUI.IdpConfig>


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

           com.example.mealdeal.R.id.save_menu ->{
                 saveMenu()
                 return true
             }

            com.example.mealdeal.R.id.sign_out_menu->{

                mAuth.signOut()

               // Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
                return true
            }



            else -> super.onOptionsItemSelected(item)
        }
    }

    fun saveMenu(){

    }
}
