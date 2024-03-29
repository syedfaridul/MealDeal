package com.example.mealdeal.util

import com.example.mealdeal.data.local.Child
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


var namesOfDays =
    arrayOf("Monday Menu", "Tuesday Menu", "Wednesday Menu", "Thursday Menu", "Friday Menu")

fun valueListener(setValueForChild: (DataSnapshot) -> Unit) {

    for (i in namesOfDays.indices) {
        var parentReference = FirebaseDatabase.getInstance().reference.child(namesOfDays[i])

        parentReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                setValueForChild(p0)

                }


            })

        }
    }
