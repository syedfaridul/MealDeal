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
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import com.google.firebase.database.core.view.Event
import com.google.firebase.firestore.local.LruGarbageCollector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.coroutines.experimental.asReference
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    private lateinit var adapter: ExpandableCardViewAdapter

    // private var listOfItem = ArrayList<Item>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: MainActivityViewModel

    private lateinit var database: DatabaseReference
    private var mMessageReference: DatabaseReference? = null
    private var mUser: FirebaseUser? = null
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    var mparent:Parent= Parent("","")
     private  var  child: Child=Child(mparent,"","","")

    // Firebase instance variables
    private lateinit var mAuth: FirebaseAuth
    private lateinit var providers: List<AuthUI.IdpConfig>
    private var RC_SIGN_IN = 123
    private var parent = arrayOf("Monday Menu", "Tuesday Menu", "Wednesday Menu", "Thursday Menu", "Friday Menu")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        database = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        mAuth = FirebaseAuth.getInstance()


        database.child("title").setValue("JavaSampleApproach")
        database.child("prosper").setValue("Bad Guy, wey too sabi")


        retrieveData()

        initBottomNav()
    }


    private fun initView() {

        //some predefined values:
        val parent1 = Parent("", getString(R.string.monday_menu))
         val childItems1 = ArrayList<Child>()
        childItems1.add(Child(parent1, "monday", "monday", "rice"))
        parent1.childItems.clear()
        parent1.childItems.addAll(childItems1)

       /* val parent2 = Parent(1, getString(R.string.tuesday_menu))
        val childItems2 = ArrayList<Child>()
        childItems2.add(Child(parent2, "", "monday", "rice", ""))
        childItems2.add(Child(parent2, "", "monday", "rice", ""))
        childItems2.add(Child(parent2, "", "monday", "rice", ""))
        parent2.childItems.clear()
        parent2.childItems.addAll(childItems2)

        val parent3 = Parent(2, getString(R.string.wednesday_menu))
        val childItems3 = ArrayList<Child>()
        childItems3.add(Child(parent3, "", "monday", "rice", ""))

        parent3.childItems.clear()
        parent3.childItems.addAll(childItems3)


        val parent4 = Parent(3, getString(R.string.thursday_menu))
        val childItems4 = ArrayList<Child>()
        childItems4.add(Child(parent4, "", "monday", "rice", ""))

        parent4.childItems.clear()
        parent4.childItems.addAll(childItems4)

        val parent5 = Parent(4, getString(R.string.friday_menu))
        val childItems5 = ArrayList<Child>()
        childItems5.add(Child(parent5, "", "monday", "rice", ""))
        parent5.childItems.clear()
        parent5.childItems.addAll(childItems5)
*/
        val itemList = ArrayList<Item>()
        itemList.add(parent1)
       /* itemList.add(parent2)
        itemList.add(parent3)
        itemList.add(parent4)
        itemList.add(parent5)*/


        val cardView = recycler_cardview
        cardView.layoutManager = LinearLayoutManager(this)



        adapter=ExpandableCardViewAdapter(itemList,this)
        cardView.adapter =adapter


        /*   val list = rootView.findViewById<RecyclerView>(R.id.list)
           list.adapter = SimpleAdapter(itemList)

           return rootView*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        val menuItem: MenuItem = menu!!.findItem(R.id.admin)

        val usid = FirebaseAuth.getInstance().currentUser!!.uid
        Log.e("uid",usid)

        var reference = FirebaseDatabase.getInstance().reference.child("administrators")


        val postListener = object : ValueEventListener {
            var event:String=""
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for (d in dataSnapshot.children) run {
                    event= d.key.toString()

                    Log.e("ref",event)

                }
                menuItem.isVisible = usid==event

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        reference.addValueEventListener(postListener)

        Log.e("ref",reference.key)

        return true
    }


    override fun onResume() {
        super.onResume()

        retrieveData()
    }

    private fun retrieveData() {

        var parent_reference = FirebaseDatabase.getInstance().reference.child(parent[0])
        //some predefined values:
        var foodImage=""
        var foodTitle= ""
        var i=0
        val parent1 = Parent("", parent_reference.key.toString())
        var childItems1 = ArrayList<Child>()

        Log.e("what this string is ", parent_reference.key.toString())

        parent_reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                        val children = p0.children
                        children.forEach {
                            foodTitle = it.child("title").value.toString()
                            foodImage = it.child("image").value.toString()
                            childItems1.add(Child(mparent, "", foodTitle, foodImage))

                        }
                        Log.e("foodImage", foodImage)
                        Log.e("foodImage", foodTitle)
                        parent1.childItems.clear()
                        parent1.childItems.addAll(childItems1)

                    }   })
        //



        val itemList = ArrayList<Item>()
        itemList.add(parent1)


        val cardView = recycler_cardview
        cardView.layoutManager = LinearLayoutManager(this)



        adapter = ExpandableCardViewAdapter(itemList, this)
        cardView.adapter = adapter



    }

    val listener = object : ValueEventListener {
        var childlist = ArrayList<Child>()
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (d in dataSnapshot.children) run {
                child= d.getValue() as Child
                childlist.add(child)

                Log.e("ref", child.toString())

            }
            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w("loadPost:onCancelled", databaseError.toException())
            // ...
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

           R.id.save_menu ->{
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
                 /*  intent.putExtra("child",child)
                   intent.putExtra("food title",child.title)
                  // intent.putExtra("id",child?.id)
                   intent.putExtra("food image", child.image)*/
               }
                   startActivity(intent)

               return true

           }
            else -> super.onOptionsItemSelected(item)
        }
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
