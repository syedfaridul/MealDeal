package com.example.mealdeal.util

import android.util.Log
import com.example.mealdeal.data.local.Child
import com.example.mealdeal.data.local.Parent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private var parent =
    arrayOf("Monday Menu", "Tuesday Menu", "Wednesday Menu", "Thursday Menu", "Friday Menu")

fun doStuff(whateverFunction: () -> Unit) {
    lateinit var parent1: Parent
    for (i in parent.indices) {

        var parentReference = FirebaseDatabase.getInstance().reference.child(parent[i])
        //some predefined values:

        parent1 = Parent("", parentReference.key.toString())
        var childItems1 = ArrayList<Child>()

        Log.e("what this string is ", parentReference.key.toString())

        parentReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                whateverFunction()
            }
        })
    }
}