package com.example.mealdeal.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealdeal.R
import com.example.mealdeal.data.local.CHILD
import com.example.mealdeal.data.local.Child
import com.example.mealdeal.data.local.Item
import com.example.mealdeal.data.local.Parent
import com.example.mealdeal.util.inflate
import com.example.mealdeal.util.loadImageWithGlide
import kotlinx.android.synthetic.main.cardview_parent.view.*



class ExpandableCardViewAdapter(private val itemList: ArrayList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return itemList[position].getItemType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHILD -> ChildViewHolder(parent.inflate(
                R.layout.cardview_child, false))
            else -> ParentViewHolder(parent.inflate(R.layout.cardview_parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CHILD -> {
                val childViewHolder = (holder as ChildViewHolder)
                childViewHolder.childItem = itemList[position] as Child
                childViewHolder.bind()
            }
            else -> {
                val parentViewHolder = holder as ParentViewHolder
                parentViewHolder.parentItem = itemList[position] as Parent
                parentViewHolder.bind()
            }
        }
    }

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val startPosition = adapterPosition + 1
                val count = parentItem.childItems.size

                if (parentItem.isExpanded) {
                    itemList.removeAll(parentItem.childItems)
                    notifyItemRangeRemoved(startPosition, count)
                    parentItem.isExpanded = false
                } else {
                    itemList.addAll(startPosition, parentItem.childItems)
                    notifyItemRangeInserted(startPosition, count)
                    parentItem.isExpanded = true
                }
                //updateViewState()
            }
        }

        lateinit var parentItem: Parent

        private val title: TextView = itemView.findViewById(R.id.item_text)

        fun bind() {
            title.text = parentItem.title
        }

        private fun updateViewState() {
            if (parentItem.isExpanded) {
                title.text = itemView.context.getString(R.string.monday_menu)
            } else {
                title.text = itemView.context.getString(R.string.monday_menu)
            }
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var childItem: Child

        private val title: TextView = itemView.findViewById(R.id.food_title)
        private val image :ImageView=itemView.findViewById(R.id.imageView)
        fun bind() {
            title.text = childItem.title
            image.loadImageWithGlide(childItem.image)

        }
    }
}