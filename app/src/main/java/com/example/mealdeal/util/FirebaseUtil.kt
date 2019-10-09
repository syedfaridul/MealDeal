package com.example.mealdeal.util

import android.widget.Toast
import com.example.mealdeal.foodie.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class FirebaseUtil (private var isAdmin:Boolean){


   fun openFbReference( ref:String, callerActivity:MainActivity)
    {
           val  mFirebaseAuth = FirebaseAuth.getInstance()
           val caller = callerActivity

            FirebaseAuth.AuthStateListener { firebaseAuth ->
                if (firebaseAuth.currentUser == null) {

                } else {
                    val userId = firebaseAuth.uid
                    checkAdmin(userId!!)
                }

            }

        }


    private fun checkAdmin(uid: String) {
        isAdmin= false
        val ref = FirebaseDatabase.getInstance().reference.child("administrators")
            .child(uid)
        val listener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                isAdmin = true
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        ref.addChildEventListener(listener)
    }

}