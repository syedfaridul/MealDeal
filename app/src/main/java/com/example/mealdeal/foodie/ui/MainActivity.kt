package com.example.mealdeal.foodie.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SimpleAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealdeal.adapter.ExpandableCardViewAdapter

import com.example.mealdeal.R
import com.example.mealdeal.data.local.Child
import com.example.mealdeal.data.local.DataManager
import com.example.mealdeal.data.local.Item
import com.example.mealdeal.data.local.Parent
import com.example.mealdeal.foodie.AdminActivity
import com.example.mealdeal.foodie.LoginActivity
import com.example.mealdeal.foodie.viewmodel.MainActivityViewModel
import com.example.mealdeal.util.JagajagaAnotherWay
import com.example.mealdeal.util.JagajagaInterface
import com.example.mealdeal.util.doStuff
import com.example.mealdeal.util.valueListener
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.HashMap
import javax.inject.Inject
import com.google.firebase.database.DataSnapshot as FirebaseDatabaseDataSnapshot


class MainActivity : DaggerAppCompatActivity(), JagajagaInterface {
    override fun onDoSomething() {

    }

    private lateinit var adapter: ExpandableCardViewAdapter

    // private var listOfItem = ArrayList<Item>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: MainActivityViewModel

    private var database: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null
    private var mUser: FirebaseUser? = null
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    var mparent: Parent = Parent(0,"", "")
    private var child: Child = Child(mparent, "", "", 0)
    private var itemList = ArrayList<Item>()
    val parentItems  = DataManager.parents
    private var recyclerView: RecyclerView?= null
    private var no =0


    // Firebase instance variables
    private lateinit var mAuth: FirebaseAuth
    private lateinit var providers: List<AuthUI.IdpConfig>
    private var RC_SIGN_IN = 123
    private var namesOfDays =
        listOf("Monday Menu", "Tuesday Menu", "Wednesday Menu", "Thursday Menu", "Friday Menu")
    private val listOfParent = listOf<Parent>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        database = FirebaseDatabase.getInstance().reference
        initView(database!!)
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            listOfParent
        }
        initBottomNav()
        doStuff {

        }

        val jagajagaAnotherWay = JagajagaAnotherWay(this)
        jagajagaAnotherWay.doStuff()

        recyclerView = findViewById<View>(R.id.recycler_cardview) as RecyclerView
       recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)

        var child = HashMap<String, Child>()
        val childItems = DataManager.childs

        val parentItem = DataManager.parents

        val parentReference = FirebaseDatabase.getInstance().reference

        parentReference.addValueEventListener(
            object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: com.google.firebase.database.DataSnapshot) {
                    for (snapShot in dataSnapshot.children) {

                        val parentKey = snapShot.key!!

                        val childReference =
                            FirebaseDatabase.getInstance().reference.child(parentKey)


                        childReference.addValueEventListener(

                            object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onDataChange(dataSnapshot: FirebaseDatabaseDataSnapshot) {
                                    val Child = ArrayList<Child>()
                                    val childItem = Child(mparent,id="",title = "",image = 0)

                                    for (snapshot1 in dataSnapshot.children) {
                                         val id = snapshot1.child("id")
                                         val title = snapshot1.child("title")
                                         val image = snapshot1.child("image")
                                         val parent = snapshot1.child("parent")
                                        childItem.id =id.toString()
                                        childItem.title = title.toString()
                                        childItem.image = no

                                        //childItem.image = image.value as Int
                                        parentItems["Monday Menu"]!!.childItems.add(no,childItem)
                                    }

                                    no += 1

                                    adapter = ExpandableCardViewAdapter(
                                        parentItems.values.sortedBy { it.id }.toMutableList(),
                                        this@MainActivity
                                    )
                                    recyclerView!!.adapter = adapter

                                }

                            }
                        )


                    }
                }

    })}


    private fun initView(databaseReference: DatabaseReference) {
        var parentId = ""
        val childItems = DataManager.childs
        val parentItems = DataManager.parents
        childItems.forEach {
            parentId = it.id.toString()
            parentItems[parentId]!!.childItems.add(it)
            //val key = databaseReference.child("Menu").push().key
            //it.id = key
            //databaseReference.child("Menu").child(key!!).setValue(it)
        }


        /*val cardView = recycler_cardview
        cardView.layoutManager = LinearLayoutManager(this)

        adapter = ExpandableCardViewAdapter(DataManager.parents.values.sortedBy { it.no }.toMutableList(), this)
        cardView.adapter = adapter*/

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        val menuItem: MenuItem = menu!!.findItem(R.id.admin)
        val usid = FirebaseAuth.getInstance().currentUser!!.uid
        Log.e("uid", usid)
        var reference = FirebaseDatabase.getInstance().reference.child("administrators")
        val postListener = object : ValueEventListener {
            var event: String = ""
            override fun onDataChange(dataSnapshot: FirebaseDatabaseDataSnapshot) {
                // Get Post object and use the values to update the UI
                for (d in dataSnapshot.children) run {
                    event = d.key.toString()
                    Log.e("ref", event)
                }
                menuItem.isVisible = usid == event // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        reference.addValueEventListener(postListener)

        Log.e("ref", reference.key)

        return true
    }

    private fun retrieveData(){
        var child = HashMap<String, Child>()
        val childItems = DataManager.childs

        val parentItem = DataManager.parents
/*
        parentItem.forEach {
            var reference = FirebaseDatabase.getInstance().reference.child(
                parentItem.values.toString()
            )
            val postListener = object : ValueEventListener {
                var childItem = Child(parent, "", "", 0)

                override fun onDataChange(dataSnapshot: FirebaseDatabaseDataSnapshot) {
                    // Get Post object and use the values to update the UI
                    for (d in dataSnapshot.children) {

                        childItem = d.getValue(Child::class.java)!!
                        var parentId = childItem.id.toString()
                        DataManager.parents[parentId]!!.childItems.add(childItem)


                        adapter = ExpandableCardViewAdapter(
                            DataManager.parents.values.sortedBy { it.no }.toMutableList(),
                            this@MainActivity
                        )
                        recyclerView!!.adapter = adapter

                        recycler_cardview.adapter!!.notifyDataSetChanged()

                    }
                    // menuItem.isVisible = usid == event
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("loadPost:onCancelled", databaseError.toException())
                }


            }
            reference.addValueEventListener(postListener)


        }
*/


    }

    override fun onResume() {
        super.onResume()
//      recycler_cardview.adapter!!.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        mAuth.removeAuthStateListener(mAuthListener)
    }


    val listener = object : ValueEventListener {
        var childlist = ArrayList<Child>()
        override fun onDataChange(dataSnapshot: FirebaseDatabaseDataSnapshot) {
            // Get Post object and use the values to update the UI
            for (d in dataSnapshot.children) run {
                child = d.value as Child
                childlist.add(child)

                Log.e("ref", child.toString())

            }
            // ...
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("loadPost:onCancelled", databaseError.toException())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_menu -> {
                return true
            }
            R.id.sign_out_menu -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        startActivity(intentFor<LoginActivity>().newTask().clearTask())
                    }
                return true
            }
            R.id.admin -> {
                val intent = Intent(this, AdminActivity::class.java).apply {
                    //  putExtra(EXTRA_MESSAGE, message)
                }
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showMenu() {
        invalidateOptionsMenu()
    }

    private fun initBottomNav() {
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationSelectedListener)
    }

    private val mOnNavigationSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {

                    true
                }
                R.id.navigation_history -> {
                    intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true

                }

                else ->
                    false
            }
        }


}
