package com.example.mealdeal.data.local

import android.media.Image
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


interface Item {
    fun getItemType(): Int
}

const val PARENT = 0
const val CHILD = 1

@Parcelize
data class Parent(var id: String, var title: String) : Parcelable, Item {
    val childItems = ArrayList<Child>()
    var isExpanded = false
    var selectedChild: Child? = null

    override fun getItemType() = PARENT
}

@Parcelize
data class Child(
    val parent: Parent?,
    var id: String?,
    var title: String?,
    var image: Int?
) : Parcelable, Item {

    override fun getItemType() = CHILD
}