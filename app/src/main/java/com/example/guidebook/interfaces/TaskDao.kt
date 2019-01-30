package com.example.guidebook.interfaces

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.guidebook.models.Task

@Dao
interface TaskDao {
    // Select all from Task table and order by "complete by" date
    @get:Query("SELECT * FROM " + "GuideBook")
    val allGuide: LiveData<MutableList<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuides(list: List<Task>)

    @Query("DELETE FROM GuideBook")
    fun deleteAll()

}