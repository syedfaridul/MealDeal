package com.example.mealdeal.foodie

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import org.jetbrains.anko.image
import java.io.FileNotFoundException


class AdminActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    private  val SELECT_IMAGE = 2
    private var ref:FirebaseDatabase=FirebaseDatabase.getInstance()
    private  var database: DatabaseReference =ref.reference
    private lateinit var storageReference:StorageReference

    var daysOfOrder = arrayOf("Monday Menu", "Tuesday Menu", "Wednesday Menu", "Thursday Menu", "Friday Menu")

    var spinner: Spinner? = null
    var parent:Parent=Parent("","")

    private lateinit var day_for_menu: String
    private var childItem: Child= Child(parent,"","",0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
       /* val intent= intent
        childItem=intent.getParcelableExtra("child")
        mtitle=intent.getStringExtra("food title")
         mimage=intent.getStringExtra("food image")*/

        database.child("prosper").setValue("Bad Guy, wey too sabi")

        initViews()
        //postToDatabase();l
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
           saveMenu()
        }
        spinner = this.spinner_items
        spinner!!.onItemSelectedListener = this
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, daysOfOrder)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = aa
    }

    private fun saveMenu(){

    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
           day_for_menu =daysOfOrder[position]
           if(database.key==null){
               database.push().setValue(day_for_menu)
               Log.e("day",day_for_menu)
           }
    }


    override fun onNothingSelected(arg0: AdapterView<*>) {
        if(database.key==null){
           // database.push().child("Monday Menu").push().setValue("Monday Menu")
        }
    }

    private fun saveMenus() {
        for (i in daysOfOrder.indices) {
            var parentReference = FirebaseDatabase.getInstance().reference.child(daysOfOrder[i])
            //some predefined values:
            var foodImage = ""
            var foodTitle = ""
            var i = 0
            var parent1 = Parent("", parentReference.key.toString())
            var childItems1 = ArrayList<Child>()

            Log.e("what this string is ", parentReference.key.toString())

            parentReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(p0: DataSnapshot) {
                    val children = p0.children
                    children.forEach {
                        foodTitle = it.child("title").value.toString()
                        foodImage = it.child("image").value.toString()
                        childItems1.add(Child(parent, "", foodTitle, 0))

                    }
                    Log.e("foodImage", foodImage)
                    Log.e("foodImage", foodTitle)
                    parent1.childItems.clear()
                    parent1.childItems.addAll(childItems1)
                }
            })
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

}

