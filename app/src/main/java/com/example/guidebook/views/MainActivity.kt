package com.example.guidebook.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guidebook.R
import com.example.guidebook.adapters.GuideBookAdapter
import com.example.guidebook.core.ViewModelFactory
import com.example.guidebook.models.Task
import com.example.guidebook.viewModels.TaskListViewModel
import java.util.*


class MainActivity : AppCompatActivity(), GuideBookAdapter.OnItemClick, Observer<MutableList<Task>>,
    View.OnClickListener {


    lateinit var binding: com.example.guidebook.databinding.ActivityMainBinding
    lateinit var viewModel: TaskListViewModel
    lateinit var adapter: GuideBookAdapter
    private lateinit var list: MutableList<Task>
    lateinit var listRecords: MutableList<Task>
    lateinit var linearLayoutManager: GridLayoutManager
    var isLoading = false
    lateinit var scrollListener: RecyclerView.OnScrollListener
    var isList = true
    var loadCount: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.example.guidebook.R.layout.activity_main)

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProviders.of(this, factory).get(TaskListViewModel::class.java)

        linearLayoutManager = GridLayoutManager(this, 1)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        listRecords = ArrayList()

        switchLayoutManager()

        list = ArrayList()
        setAdapter(list)

        setListener()


    }

    private fun setListener() {
        binding.ivList.setOnClickListener(this)

        scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (!isLoading && lastVisibleItemPosition >= adapter.list.size - 1 && adapter.list.size < listRecords.size) {
                        isLoading = true
                        binding.progressBar.visibility = View.VISIBLE

                        var handler = Handler() // delay to lazy loading
                        handler.postDelayed({
                            handler.removeCallbacksAndMessages(null)
                            isLoading = false
                            binding.progressBar.visibility = View.GONE
                            loadMore(lastVisibleItemPosition + 1)
                        }, 1500)
                    }
                }
            }
        }
        binding.rvGuides.addOnScrollListener(scrollListener)
    }

    private fun setAdapter(list: MutableList<Task>) {

        adapter = GuideBookAdapter(list, this, this, isList)
        binding.rvGuides.adapter = adapter
        viewModel.getTasks().observe(this, this)

    }

    private fun loadMore(indexToFetch: Int) {
        lateinit var subList: MutableList<Task>
        if (indexToFetch < listRecords.size && indexToFetch + loadCount < listRecords.size) {
            subList = listRecords.subList(indexToFetch, indexToFetch + loadCount)
            binding.rvGuides.removeOnScrollListener(scrollListener)
            adapter.setTaskList(subList)
            binding.rvGuides.addOnScrollListener(scrollListener)
        } else if (indexToFetch < listRecords.size) {
            subList = listRecords.subList(indexToFetch, listRecords.size)
            binding.rvGuides.removeOnScrollListener(scrollListener)
            adapter.setTaskList(subList)
            binding.rvGuides.addOnScrollListener(scrollListener)
        }
    }

    override fun onItemClick(pos: Int) {
        val i = Intent(this, WebViewActivity::class.java)
        i.putExtra("url", "https://guidebook.com" + list[pos].url)
        i.putExtra("title", list[pos].name)
        startActivity(i)
    }

    override fun onChanged(tasks: MutableList<Task>?) {
        if (tasks != null && tasks.size > 0 && viewModel.isLoaded) {
            listRecords.clear()
            listRecords.addAll(tasks)
            adapter.list.clear()
            loadMore(0)
            viewModel.getTasks().removeObserver(this)
        }
    }

    fun switchLayoutManager() {
        if (isList) {
            linearLayoutManager.spanCount = 1
            binding.rvGuides.layoutManager = linearLayoutManager
        } else {
            linearLayoutManager.spanCount = 2
            binding.rvGuides.layoutManager = linearLayoutManager
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivList -> {
                if (isList) {
                    isList = false
                    loadCount = 6
                    binding.ivList.setBackgroundResource(R.drawable.ic_list)
                    switchLayoutManager()
                    setAdapter(list)
                } else {
                    isList = true
                    loadCount = 3
                    binding.ivList.setBackgroundResource(R.drawable.ic_grid)
                    switchLayoutManager()
                    setAdapter(list)
                }
            }
        }
    }
}
