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
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_admin.*
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.*
import com.example.mealdeal.data.local.Parent
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.cardview_child.*
import org.jetbrains.anko.image
import java.io.FileNotFoundException


class AdminActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    private  val SELECT_IMAGE = 2
    private var ref:FirebaseDatabase=FirebaseDatabase.getInstance()
    private  var database: DatabaseReference =ref.reference
    private lateinit var storageReference:StorageReference

    var languages = arrayOf("Monday Menu", "Tuesday Menu", "Wednesday Menu", "Thursday Menu", "Friday Menu")

    var spinner: Spinner? = null
    var parent:Parent=Parent("","")

    private lateinit var day_for_menu: String
    private var childItem: Child= Child(parent,"","","")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
       /* val intent= intent
        childItem=intent.getParcelableExtra("child")
        mtitle=intent.getStringExtra("food title")
         mimage=intent.getStringExtra("food image")*/

        database.child("prosper").setValue("Bad Guy, wey too sabi")

        initViews()
        //postToDatabase()
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

            childItem.id=database.child(day_for_menu).push().key
            childItem.title=foodEditText.text.toString()
            childItem.image=food_image.image.toString()
            childItem.parent!!.id=day_for_menu
            childItem.parent!!.title=day_for_menu
            childItem.id?.let { it1 -> database.child(day_for_menu).child(it1).setValue(childItem) }
           finish()

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
           day_for_menu =languages[position]
           if(database.key==null){
               database.child(day_for_menu).setValue(day_for_menu)
               Log.e("day",day_for_menu)
           }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
        if(database.key==null){
            database.push().child("Monday Menu").setValue("Monday Menu")
        }
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


    private fun mealRef() {

        var menu_reference = FirebaseDatabase.getInstance().reference.child("administrators")


        val menuListener = object : ValueEventListener {
            var childlist = ArrayList<Child>()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for (d in dataSnapshot.children)  {
                    childItem= d.getValue() as Child
                    childlist.add(childItem)

                    Log.e("ref","good")

                }

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        menu_reference.addValueEventListener(menuListener)
    }



    private fun saveFoodDeal(){
        childItem.id=database.child(day_for_menu).push().key
    }







}

