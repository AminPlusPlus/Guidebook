package com.example.guidebook.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.guidebook.adapters.GuideBookAdapter
import com.example.guidebook.core.ViewModelFactory
import com.example.guidebook.models.Task
import com.example.guidebook.viewModels.TaskListViewModel


class MainActivity : AppCompatActivity(), GuideBookAdapter.OnItemClick {

    lateinit var binding: com.example.guidebook.databinding.ActivityMainBinding
    lateinit var viewModel: TaskListViewModel
    lateinit var adapter: GuideBookAdapter
    lateinit var list: List<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, com.example.guidebook.R.layout.activity_main)
        binding.executePendingBindings()

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProviders.of(this, factory).get(TaskListViewModel::class.java)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvGuides.layoutManager = linearLayoutManager

        list = ArrayList()
        adapter = GuideBookAdapter(list, this, this)

        viewModel.getTasks().observe(this,
            Observer<PagedList<Task>> { tasks ->
                adapter.submitList(tasks!!)
            })
        binding.rvGuides.adapter = adapter
    }

    override fun onItemClick(pos: Int) {
        val i = Intent(this, WebViewActivity::class.java)
        i.putExtra("url", "https://guidebook.com" + adapter.list.get(pos).url)
        i.putExtra("title", adapter.list.get(pos).name)
        startActivity(i)
    }

}