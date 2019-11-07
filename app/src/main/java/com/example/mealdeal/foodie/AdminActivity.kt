package com.example.mealdeal.foodie

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mealdeal.data.local.Child
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_admin.*
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.*
import android.widget.Toast.*
import com.example.mealdeal.R

import com.example.mealdeal.data.local.Parent
import com.example.mealdeal.util.NOTE_POSITION
import com.example.mealdeal.util.POSITION_NOT_SET
import java.io.FileNotFoundException
import java.util.HashMap
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


import com.example.mealdeal.foodie.ui.MainActivity


class AdminActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var notePosition = POSITION_NOT_SET

    private val SELECT_IMAGE = 2
    private var ref: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var database: DatabaseReference = ref.reference
    private lateinit var storageReference: StorageReference
    private var daysOfOrder = arrayOf("Monday Menu", "Tuesday Menu", "Wednesday menu", "Thursday Menu", "Friday Menu")
    private var spinner: Spinner? = null
    private lateinit var childNew: Child
    private var parent: Parent = Parent(0,"", "")
    private var dayForMenu = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mealdeal.R.layout.activity_admin)
        initViews()

        saveMenu()

    }

    private fun initViews() {
        upload_button.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE)
        }

        spinner = this.spinner_items
        spinner!!.onItemSelectedListener = this
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, daysOfOrder)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = aa
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putInt(NOTE_POSITION, notePosition)
    }

    private fun saveMenu() {

        val foodTitle = findViewById<EditText>(com.example.mealdeal.R.id.foodEditText)
        val foodPics = findViewById<ImageView>(com.example.mealdeal.R.id.food_image)
        val childItemId = dayForMenu


        button_add_meal.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference(dayForMenu)

            val child = HashMap<String, Child>()
            child.put(foodTitle.text.toString(), Child(parent,childItemId,foodTitle.text.toString(),foodPics.id))
            ref.setValue(child)

        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        dayForMenu = daysOfOrder[position]

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
        if (database.key == null) {
            // database.push().child("Monday Menu").push().setValue("Monday Menu")
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data!!.data
                val imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                food_image.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                makeText(this, "Something went wrong", LENGTH_LONG).show()
            }

        } else {
            makeText(this, "You haven't picked Image", LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("on pause", "onPause")
        saveMenu()

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)

        intent.putExtra("value_key", dayForMenu)
        startActivity(intent)
        super.onBackPressed()
    }


}

