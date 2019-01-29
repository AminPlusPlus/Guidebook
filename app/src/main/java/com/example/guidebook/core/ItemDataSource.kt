package com.example.guidebook.core

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.paging.PageKeyedDataSource
import com.example.guidebook.models.Task

class ItemDataSource(internal var application: Application) : PageKeyedDataSource<Int, Task>() {
    private val mTasks: LiveData<List<Task>> = GuideBook.getDatabaseInstance(application).taskDao().allGuide


    //this will be called once to load the initial data
    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, Task>
    ) {
        val l = mTasks.value!!.subList(FIRST_PAGE, PAGE_SIZE)

        callback.onResult(l, null, FIRST_PAGE + 1)

    }

    //this will load the previous page
    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Task>
    ) {

        val adjacentKey = if (params.key > 1) params.key - PAGE_SIZE else null
        val l = mTasks.value!!.subList(params.key, params.key - PAGE_SIZE)
        callback.onResult(l, adjacentKey)

    }

    //this will load the next page
    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Task>
    ) {
        val l = mTasks.value!!.subList(params.key, params.key + PAGE_SIZE)

        //if the response has next page
        //incrementing the next page number
        val key = if (mTasks.value!!.size > params.key + PAGE_SIZE) params.key + PAGE_SIZE else null

        //passing the loaded data and next page value
        callback.onResult(l, key)


    }

    companion object {

        //the size of a page that we want
        val PAGE_SIZE = 3

        //we will start from the first page which is 1
        private val FIRST_PAGE = 0

        //we need to fetch from stackoverflow
        private val SITE_NAME = "stackoverflow"
    }
}