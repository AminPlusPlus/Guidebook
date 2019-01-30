package com.example.guidebook.adapters

import android.app.Activity
import androidx.paging.PagedListAdapter
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.guidebook.R
import com.example.guidebook.core.PersonDiffCallback
import com.example.guidebook.models.Task
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_guide.view.*

class GuideBookAdapter(
    l: MutableList<Task>,
    c: Context,
    lstnr: OnItemClick,
    islist: Boolean
) :
    PagedListAdapter<Task, GuideBookAdapter.PersonViewHolder>(PersonDiffCallback()) {
    var list: MutableList<Task> = l
    var isList = islist
    private var context: Context = c
    private var listener: OnItemClick = lstnr

    interface OnItemClick {
        fun onItemClick(pos: Int)
    }

    fun setTaskList(lists: MutableList<Task>) {
        list.addAll(lists)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holderPerson: PersonViewHolder, position: Int) {
        var task = list[position]

        holderPerson.tvName.text = task.name
        Picasso.get().load(task.icon).fit().error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher).into(holderPerson.ivGuide)
        holderPerson.tvVenue.visibility = View.GONE
        holderPerson.tvEndDate.text = "End Date: " + task.endDate
        holderPerson.tvStartDate.text = "Start Date: " + task.startDate
        holderPerson.itemView.setOnClickListener { listener.onItemClick(position) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        if (isList) {
            val itemView = LayoutInflater.from(context).inflate(
                R.layout.layout_guide,
                parent, false
            )

            val height = (parent.measuredHeight / 3) + 1
            itemView.layoutParams.height = height

            return PersonViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(context).inflate(
                R.layout.layout_guide_grid,
                parent, false
            )

            val height = (parent.measuredHeight / 3) + 1
            val width = (parent.measuredWidth / 2)
            itemView.layoutParams.height = height
            itemView.layoutParams.width = width

            return PersonViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.tvName
        var tvStartDate: TextView = view.tvStartDate
        var tvEndDate: TextView = view.tvEndDate
        var tvVenue: TextView = view.tvVenue
        var ivGuide: ImageView = view.ivGuide
        var linRoot: LinearLayout = view.linRoot

        fun setHeight() {
            val displaymetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)

            val deviceheight = displaymetrics.heightPixels / 3
            linRoot.layoutParams.height = deviceheight
        }

    }

}