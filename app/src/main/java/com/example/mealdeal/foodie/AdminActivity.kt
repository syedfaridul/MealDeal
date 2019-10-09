package com.example.mealdeal.foodie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mealdeal.R
import com.example.mealdeal.data.local.Child
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    val childItem:Child? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

     initViews()
    }


    fun initViews(){

        button.setOnClickListener {
            foodDeal()
        }
    }


    fun foodDeal(){

        childItem!!.title=foodEditText.text.toString()


        database.push().setValue(childItem)

    }
}
