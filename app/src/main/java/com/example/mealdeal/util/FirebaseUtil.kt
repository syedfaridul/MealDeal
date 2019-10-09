package com.example.mealdeal.util

class FirebaseUtil {
    /*lateinit var mFirebaseDatabase: FirebaseDatabase
    lateinit var mDatabaseReference: DatabaseReference
    var firebaseUtil: FirebaseUtil? = FirebaseUtil()
     lateinit var mFirebaseAuth: FirebaseAuth
    lateinit var mStorage: FirebaseStorage
    lateinit var mStorageRef: StorageReference
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mDeals: ArrayList<MealDeal>
    private val RC_SIGN_IN = 123
    private var caller: LoginActivity =LoginActivity()
    var isAdmin: Boolean = false


   fun openFbReference(ref: String, callerActivity: LoginActivity) {
        if (firebaseUtil == null) {
            firebaseUtil = FirebaseUtil()
            mFirebaseDatabase = FirebaseDatabase.getInstance()
            mFirebaseAuth = FirebaseAuth.getInstance()
            caller = callerActivity

            mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                if (firebaseAuth.currentUser == null) {
                    firebaseUtil?.signIn()
                } else {
                    val userId = firebaseAuth.uid
                    checkAdmin(userId)
                }
                Toast.makeText(callerActivity.getBaseContext(), "Welcome back!", Toast.LENGTH_LONG)
                    .show()
            }
            connectStorage()

        }

        mDeals = ArrayList<MealDeal>()
        mDatabaseReference = mFirebaseDatabase.reference.child(ref)
    }




    private fun signIn() {
        // Choose authentication providers
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        caller.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(),
            RC_SIGN_IN
        )
    }




    private fun checkAdmin(uid: String?) {
        firebaseUtil!!.isAdmin = false
        val ref = mFirebaseDatabase.reference.child("administrators")
            .child(uid!!)
        val listener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                firebaseUtil?.isAdmin = true
                caller.showMenu()
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



    fun attachListener() {
        mFirebaseAuth.addAuthStateListener(mAuthListener)
    }

    fun detachListener() {
        mFirebaseAuth.removeAuthStateListener(mAuthListener)
    }

    fun connectStorage() {
        mStorage = FirebaseStorage.getInstance()
        mStorageRef = mStorage.getReference().child("deals_pictures")
    }


     companion object {

         @JvmStatic
         fun newInstance() =
             FirebaseUtil().apply {
                *//* arguments = Bundle().apply {
                 }*//*
             }
     }
*/}