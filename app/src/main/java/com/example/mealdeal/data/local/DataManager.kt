package com.example.mealdeal.data.local

import androidx.core.content.res.TypedArrayUtils.getString
import com.example.mealdeal.R

object DataManager {

    val parents = HashMap<String,Parent>()
    val childs = ArrayList<Child>()

    init {
        initializeParent()
        initializeChild()
    }



    fun addChild(parent: Parent, id: String, title: String, image: Int?):Int{
        val child = Child(parent, id ,  title , image)
        childs.add(child)
        return childs.lastIndex
    }

     fun initializeParent() {
        var parent = Parent("1", "Monday Menu")
         parents[parent.id] = parent

        parent= Parent("2", "Tuesday Menu")
        parents[parent.id] = parent

         parent = Parent("3", "Wednesday Menu")
        parents[parent.id]= parent

         parent = Parent("4", "Thursday")
        parents[parent.id] =  parent

        parent= Parent("5", "Friday Menu")
        parents[parent.id] = parent

    }

    private fun initializeChild() {
        var parent = parents["1"]
        var child = Child(parent, "1", "Rice and Chicken", R.drawable.user_profile_image_backgroung)
        childs.add(child)

        child = Child(parent, "1", " Semo and Eforiro", R.drawable.user_profile_image_backgroung)
        childs.add(child)
    }
}