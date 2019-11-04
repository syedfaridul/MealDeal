package com.example.mealdeal.foodie.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

    private lateinit var database: DatabaseReference
    private var mMessageReference: DatabaseReference? = null
    private var mUser: FirebaseUser? = null
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    var mparent: Parent = Parent("", "")
    private var child: Child = Child(mparent, "", "", 0)
    private var itemList = ArrayList<Item>()

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
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        mAuth = FirebaseAuth.getInstance()

        database.child("title").setValue("JavaSampleApproach")
        database.child("prosper").setValue("Bad Guy, wey too sabi")
        mAuthListener = FirebaseAuth.AuthStateListener {
            listOfParent
        }

        initView()
        initBottomNav()
        doStuff {
            /*val children = p0.children
            children.forEach {
            }

            parent1.childItems.clear()
            parent1.childItems.addAll(childItems1)*/
        }

        val jagajagaAnotherWay = JagajagaAnotherWay(this)
        jagajagaAnotherWay.doStuff()
    }

    private fun initView() {
        var parentId = ""
        val childItems = DataManager.childs
        val parentItems = DataManager.parents
        childItems.forEach {
            parentId = it.id.toString()
            parentItems[parentId]!!.childItems.add(it)
        }

        val cardView = recycler_cardview
        cardView.layoutManager = LinearLayoutManager(this)

        adapter = ExpandableCardViewAdapter(DataManager.parents.values.toMutableList(), this)
        cardView.adapter = adapter

    }
/*
    private fun initView() {
        //some predefined values:
      */
/*  val parent1 = Parent("", getString(R.string.monday_menu))
         var childItems1 = ArrayList<Child>()
      //  childItems1.add(Child(parent1, "monday", "monday", "rice"))
        valueListener {


            val children = p0.children
            children.forEach {
                Log.e("datasnapshot", it.key.toString())

                child.image = it.child("image").value.toString()
                Log.e("datasnapshot", child.image.toUInt())

                child.title = it.child("title").value.toString()
                child.id = it.child("id").value.toString()
                Log.e("", "the value for the num ${child.title}")

                childItems1.add(Child(mparent, child.id, child.title, child.image))
            }
            }
        parent1.childItems.clear()
        parent1.childItems.addAll(childItems1)*//*



 */
/*val parent2 = Parent("", getString(R.string.tuesday_menu))
        val childItems2 = ArrayList<Child>()
        valueListener {
            setValue(p0=it,childItems = childItems2)
            childItems2.add(Child(parent1,child.id,child.title,child.image))

        }
        parent2.childItems.clear()
        parent2.childItems.addAll(childItems2)

        val parent3 = Parent("", getString(R.string.wednesday_menu))
        val childItems3 = ArrayList<Child>()
        valueListener {
            setValue(p0=it,childItems = childItems3)
            childItems3.add(Child(parent1,child.id,child.title,child.image))

        }
        parent3.childItems.clear()
        parent3.childItems.addAll(childItems3)
*//*



      */
/*  val parent5 = Parent(4, getString(R.string.friday_menu))
        val childItems5 = ArrayList<Child>()
        childItems5.add(Child(parent5, "", "monday", "rice", ""))
        parent5.childItems.clear()
        parent5.childItems.addAll(childItems5)
*//*

 val itemList = ArrayList<Item>()
        itemList.add(parent1)

   itemList.add(parent2)
        itemList.add(parent3)
        itemList.add(parent4)
        itemList.add(parent5)



        val cardView = recycler_cardview
        cardView.layoutManager = LinearLayoutManager(this)

        adapter=ExpandableCardViewAdapter(itemList,this)
        cardView.adapter =adapter


*/
/*
 val list = rootView.findViewById<RecyclerView>(R.id.list)
           list.adapter = SimpleAdapter(itemList)

           return rootView
*//*


    }
*/

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
                menuItem.isVisible = usid == event
                // ...
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

    override fun onResume() {
        super.onResume()
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
