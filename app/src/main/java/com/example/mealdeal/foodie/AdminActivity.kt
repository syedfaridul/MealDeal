package com.example.mealdeal.foodie

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mealdeal.R
import com.example.mealdeal.data.local.Child
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_admin.*
import android.graphics.BitmapFactory
import android.widget.*
import org.jetbrains.anko.image
import java.io.FileNotFoundException


class AdminActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    private  val SELECT_IMAGE = 2
    private lateinit var database: DatabaseReference
    private lateinit var storageReference:StorageReference

    var languages = arrayOf("Monday Menu", "Tuesday Menu", "Wednesday Menu", "Thursday Menu", "Friday Menu")

    var spinner: Spinner? = null
    var mimage: String?=null
    var mtitle: String?=null


    private val childItem: Child? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mealdeal.R.layout.activity_admin)
        val intent= intent
         mtitle=intent.getStringExtra(childItem?.title)
         mimage=intent.getStringExtra(childItem?.image)


        initViews()
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


        button_add_meal.setOnClickListener {
            SaveFoodDeal()
        }



        spinner = this.spinner_items
        spinner!!.onItemSelectedListener = this

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


/* StorageUtil.uploadMessageImage(selectedImageBytes) { imagePath ->
                val messageToSend =
                    ImageMessage(imagePath, Calendar.getInstance().time,
                        FirebaseAuth.getInstance().currentUser!!.uid,
                        otherUserId, currentUser.name)
                FirestoreUtil.sendMessage(messageToSend, currentChannelId)
            }*//*

        }
    }
*/

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
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    //            storageReference = FirebaseStorage.getInstance().reference.child("deals_pictures")

    private fun SaveFoodDeal(){

        mtitle=foodEditText.text.toString()
        mimage=food_image.image.toString()
        if (childItem!!.id==null) {
            database.push().setValue(childItem)
        } else {
            database.child(childItem.id!!).setValue(childItem)
        }
        database.push().setValue(childItem)

    }








}

