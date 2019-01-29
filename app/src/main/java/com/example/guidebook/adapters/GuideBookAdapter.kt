package com.example.guidebook.adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.guidebook.R
import com.example.guidebook.core.PersonDiffCallback
import com.example.guidebook.models.Task
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_guide.view.*


class GuideBookAdapter(l: List<Task>, c: Context, lstnr: OnItemClick) :
    PagedListAdapter<Task, GuideBookAdapter.PersonViewHolder>(PersonDiffCallback()) {
    var list: List<Task> = l
    private var context: Context = c
    public var listener: OnItemClick = lstnr

    interface OnItemClick {
        fun onItemClick(pos: Int)
    }

    fun setTaskList(lists: List<Task>) {
        this.list = lists
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holderPerson: PersonViewHolder, position: Int) {
        var task = getItem(position)

        if (task == null) {
            holderPerson.clear()

        } else {
            holderPerson.tvName.text = task.name
            Picasso.get().load(task.icon).fit().error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher).into(holderPerson.ivGuide)
            holderPerson.tvVenue.visibility = View.GONE
            holderPerson.tvEndDate.text = "End Date: " + task.endDate
            holderPerson.tvStartDate.text = "Start Date: " + task.startDate
            holderPerson.itemView.setOnClickListener { view -> listener.onItemClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_guide,
                parent, false
            )
        )
    }


    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.tvName
        var tvStartDate: TextView = view.tvStartDate
        var tvEndDate: TextView = view.tvEndDate
        var tvVenue: TextView = view.tvVenue
        var ivGuide: ImageView = view.ivGuide

        fun clear() {
            tvName.text = null
        }
    }

  /*  override fun getItemCount(): Int {
        return list.size
    }
*/


/*
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
*/

    /*  override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
          val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
          val rowView = inflater.inflate(R.layout.layout_guide, p2, false)
          val textView = rowView.findViewById(R.id.tvName) as TextView
          val tvVenue = rowView.findViewById(R.id.tvVenue) as TextView
          val tvEndDate = rowView.findViewById(R.id.tvEndDate) as TextView
          val tvStartDate = rowView.findViewById(R.id.tvStartDate) as TextView
          val ivGuide = rowView.findViewById(R.id.ivGuide) as ImageView

          textView.text = "Name: " + list.get(position).name
          Picasso.get().load(list.get(position).icon).fit().error(R.mipmap.ic_launcher)
              .placeholder(R.mipmap.ic_launcher).into(ivGuide)
  *//*
        if (list.get(position).venue.size > 0) {
            tvVenue.text = "City: " + list.get(position).venue[0].toString()
            tvVenue.visibility = View.VISIBLE
        } else*//*
        tvVenue.visibility = View.GONE

        tvEndDate.text = "End Date: " + list.get(position).endDate
        tvStartDate.text = "Start Date: " + list.get(position).startDate
        // change the icon for Windows and iPhone
        rowView.setOnClickListener { listener.onItemClick(position) }

        return rowView
    }

    override fun getItem(p0: Int): Any {
        return list.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }*/

}