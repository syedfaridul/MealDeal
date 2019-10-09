package com.example.mealdeal.data.local

import android.media.Image


interface Item {
    fun getItemType(): Int
}

const val PARENT = 0
const val CHILD = 1

data class Parent(val id: Long,val title: String) : Item {
    val childItems = ArrayList<Child>()
    var isExpanded = false
    var selectedChild: Child? = null

    override fun getItemType() = PARENT
}

data class Child(
    val parent: Parent,
    val id: Long,
    val title: String,
   val image: String,
    var price: Int = 0) : Item {

    override fun getItemType() = CHILD
}