package com.example.mealdeal.foodie.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealdeal.adapter.ExpandableCardViewAdapter

import com.example.mealdeal.R
import com.example.mealdeal.data.local.Child
import com.example.mealdeal.data.local.Item
import com.example.mealdeal.data.local.Parent
import com.example.mealdeal.foodie.LoginActivity
import com.example.mealdeal.foodie.viewmodel.MainActivityViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
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


    // Firebase instance variables
    private lateinit var mAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser?=null
    private lateinit var providers:List<AuthUI.IdpConfig>
    private var RC_SIGN_IN=123



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
        childItems1.add(Child(parent1, 0, "test 1",""))
        childItems1.add(Child(parent1, 1, "test 2",""))
        childItems1.add(Child(parent1, 2, "test 3",""))
        childItems1.add(Child(parent1, 3, "test 4",""))
        parent1.childItems.clear()
        parent1.childItems.addAll(childItems1)

        val parent2 = Parent(1,getString(R.string.tuesday_menu))
        val childItems2 = ArrayList<Child>()
        childItems2.add(Child(parent2, 9, "test 5",""))
        childItems2.add(Child(parent2, 10, "test 6",""))
        childItems2.add(Child(parent2, 11, "test 7",""))
        parent2.childItems.clear()
        parent2.childItems.addAll(childItems2)

        val parent3 = Parent(2,getString(R.string.wednesday_menu))
        val childItems3 = ArrayList<Child>()
        childItems3.add(Child(parent3, 12, "test 8",""))
        childItems3.add(Child(parent3, 13, "test 9",""))
        childItems3.add(Child(parent3, 14, "test 10",""))
        parent3.childItems.clear()
        parent3.childItems.addAll(childItems3)


        val parent4 = Parent(3,getString(R.string.thursday_menu))
        val childItems4 = ArrayList<Child>()
        childItems4.add(Child(parent4, 15, "test 8",""))
        childItems4.add(Child(parent4, 16, "test 9",""))
        childItems4.add(Child(parent4, 17, "test 10",""))
        parent4.childItems.clear()
        parent4.childItems.addAll(childItems4)

        val parent5 = Parent(4,getString(R.string.friday_menu))
        val childItems5 = ArrayList<Child>()
        childItems5.add(Child(parent5, 18, "test 8",""))
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

        return true
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

            else -> super.onOptionsItemSelected(item)
        }
    }


    fun saveMenu(){

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
