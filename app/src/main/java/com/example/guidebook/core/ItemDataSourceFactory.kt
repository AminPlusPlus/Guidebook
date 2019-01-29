package com.example.guidebook.core

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.example.guidebook.models.Task

class ItemDataSourceFactory(var application: Application) : DataSource.Factory<Int, Task>() {

    //creating the mutable live data
    //getter for itemlivedatasource
    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Task>>()

    override fun create(): DataSource<Int, Task> {
        //getting our data source object
        val itemDataSource = ItemDataSource(application)

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource)

        //returning the datasource
        return itemDataSource
    }
}