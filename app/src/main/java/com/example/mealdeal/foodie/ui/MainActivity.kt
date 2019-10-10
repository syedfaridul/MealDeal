package com.example.mealdeal.foodie.ui

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealdeal.adapter.ExpandableCardViewAdapter

import com.example.mealdeal.R
import com.example.mealdeal.auth.ui.AuthActivity
import com.example.mealdeal.data.local.Child
import com.example.mealdeal.data.local.Item
import com.example.mealdeal.data.local.Parent
import com.example.mealdeal.foodie.AdminActivity
import com.example.mealdeal.foodie.LoginActivity
import com.example.mealdeal.foodie.viewmodel.MainActivityViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.firestore.local.LruGarbageCollector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    private lateinit var adapter:ExpandableCardViewAdapter

   // private var listOfItem = ArrayList<Item>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: MainActivityViewModel

    private lateinit var database: DatabaseReference
    private var mMessageReference: DatabaseReference? = null
    private var mUser: FirebaseUser?=null
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    var parent:Parent?=null
    val child:Child?=null

    // Firebase instance variables
    private lateinit var mAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser?=null
    private lateinit var providers:List<AuthUI.IdpConfig>
    private var RC_SIGN_IN=123
    var isAdmin:Boolean? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        database = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        mAuth = FirebaseAuth.getInstance()


        database.child("title").setValue("JavaSampleApproach")

        initView()

        initBottomNav()
    }


    private fun initView(){

        //some predefined values:
        val parent1 = Parent(0,getString(R.string.monday_menu))
        val childItems1 = ArrayList<Child>()
        childItems1.add(Child(parent1, "","monday", "rice",""))
        parent1.childItems.clear()
        parent1.childItems.addAll(childItems1)

        val parent2 = Parent(1,getString(R.string.tuesday_menu))
        val childItems2 = ArrayList<Child>()
        childItems2.add(Child(parent2, "","monday", "rice",""))
        childItems2.add(Child(parent2, "","monday", "rice",""))
        childItems2.add(Child(parent2, "","monday", "rice",""))
        parent2.childItems.clear()
        parent2.childItems.addAll(childItems2)

        val parent3 = Parent(2,getString(R.string.wednesday_menu))
        val childItems3 = ArrayList<Child>()
        childItems3.add(Child(parent3, "","monday", "rice",""))

        parent3.childItems.clear()
        parent3.childItems.addAll(childItems3)


        val parent4 = Parent(3,getString(R.string.thursday_menu))
        val childItems4 = ArrayList<Child>()
        childItems4.add(Child(parent4, "","monday", "rice",""))

        parent4.childItems.clear()
        parent4.childItems.addAll(childItems4)

        val parent5 = Parent(4,getString(R.string.friday_menu))
        val childItems5 = ArrayList<Child>()
        childItems5.add(Child(parent5, "","monday", "rice",""))
        parent5.childItems.clear()
        parent5.childItems.addAll(childItems5)

        val itemList = ArrayList<Item>()
        itemList.add(parent1)
        itemList.add(parent2)
        itemList.add(parent3)
        itemList.add(parent4)
        itemList.add(parent5)


        val cardView = recycler_cardview
        cardView.layoutManager = LinearLayoutManager(this)
        adapter=ExpandableCardViewAdapter(itemList)
        cardView.adapter =adapter


        /*   val list = rootView.findViewById<RecyclerView>(R.id.list)
           list.adapter = SimpleAdapter(itemList)

           return rootView*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.save_menu,menu)
        val menuItem:MenuItem=menu!!.findItem(R.id.admin)
          var userId=""
         FirebaseAuth.AuthStateListener{

              userId=it.uid!!

             readAdmin(userId)


         }
        val usid =FirebaseAuth.getInstance().currentUser!!.uid
         val uid=""
        var reference=FirebaseDatabase.getInstance().reference.child("administrators")
            .child(usid).key.toString()

       /* val uid =FirebaseAuth.getInstance().currentUser!!.uid
        Log.e("uid",uid)
        val reference=FirebaseDatabase.getInstance().reference.child("administrator").key.toString()
        val ref = FirebaseDatabase.getInstance().reference.child("administrators").child(uid).key.toString()
        Log.e("ref",reference)*/
        menuItem.isVisible =usid==reference

        return true
    }


    private fun readAdmin(uid:String){

        isAdmin=false
        var reference:DatabaseReference=FirebaseDatabase.getInstance().reference.child("administrators")
            .child(uid)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                  isAdmin=true
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        reference.addValueEventListener(postListener)

        val value = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                isAdmin = true
                this@MainActivity.showMenu()

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        reference.addChildEventListener(value)

    }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

           R.id.save_menu ->{
                 saveMenu()
                 return true
             }

           R.id.sign_out_menu->{
               AuthUI.getInstance()
                   .signOut(this)
                   .addOnCompleteListener {
                       startActivity(intentFor<LoginActivity>().newTask().clearTask())
                   }
                return true
            }
           R.id.admin-> {

               val intent = Intent(this, AdminActivity::class.java).apply {
                   //  putExtra(EXTRA_MESSAGE, message)
                 putExtra("food title",child!!.title)
                   putExtra("id",child.id)
                   putExtra("food image", child.image)
               }
                   startActivity(intent)

               return true

           }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun saveMenu(){

    }

    fun showMenu(){
        invalidateOptionsMenu()
    }

    private fun initBottomNav() {
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationSelectedListener)
    }



    private val mOnNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home-> {

                true
            }
            R.id.navigation_history-> {
                intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true

            }

            else ->
                false
        }
    }


}
