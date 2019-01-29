package com.example.guidebook.interfaces

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.example.guidebook.models.Task

@Dao
interface TaskDao {
    // Select all from Task table and order by "complete by" date
    @get:Query("SELECT * FROM " + "GuideBook")
    val allGuide: LiveData<List<Task>>

    // Select one task from Task table by id
    @Query("SELECT * FROM " + "GuideBook" + " WHERE id=:id")
    fun getGuideById(id: String): LiveData<Task>

    @Query("SELECT * FROM GuideBook")
    fun getAllPaged(): DataSource.Factory<Int, Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuides(list: List<Task>)

    @Query("DELETE FROM GuideBook")
    fun deleteAll()

}