package com.example.mealdeal.data.local

import com.example.mealdeal.R

object DataManager {

    val parents = HashMap<String,Parent>()
    val childs = ArrayList<Child>()

    init {
        initializeParent()
        initializeChild()
    }



    fun addChild(parent: Parent, id: String, title: String, image: Int):Int{
        val child = Child(parent, id ,  title , image)
        childs.add(child)
        return childs.lastIndex

    }


     fun initializeParent() {
        var parent = Parent(0,"Monday Menu", "Monday Menu")
         parents[parent.id] = parent

        parent= Parent(1,"Tuesday Menu", "Tuesday Menu")
        parents[parent.id] = parent

         parent = Parent(2,"Wednesday Menu", "Wednesday Menu")
        parents[parent.id]= parent

         parent = Parent(3,"Thursday Menu", "Thursday Menu")
        parents[parent.id] =  parent

        parent= Parent(4,"Friday Menu", "Friday Menu")
        parents[parent.id] = parent

    }

    private fun initializeChild() {
        var parent = parents["Monday Menu"]
        var child = Child(parent, "Monday Menu", "Rice and Chicken", R.drawable.user_profile_image_backgroung)
        childs.add(child)

        child = Child(parent, "Tuesday Menu", " Semo and Eforiro", R.drawable.user_profile_image_backgroung)
        childs.add(child)
    }
}